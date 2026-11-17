#!/usr/bin/env bash
set -euo pipefail

project_root=$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd -P)
work_dir=$(mktemp -d /tmp/lab7-tests.XXXXXX)
trap 'rm -r "$work_dir"' EXIT

classes_dir="$work_dir/classes"
mkdir -p "$classes_dir"

mapfile -t production_sources < <(find "$project_root/src" -name '*.java' -print | sort)
mapfile -t test_sources < <(find "$project_root/tests" -name '*.java' -print | sort)

echo "[1/6] Compilando con Java 21, -Xlint:all y -Werror"
javac --release 21 -encoding UTF-8 -Xlint:all -Werror -d "$classes_dir" \
    "${production_sources[@]}" "${test_sources[@]}"
echo "Compilación limpia: sin errores ni advertencias"

echo "[2/6] Ejecutando pruebas del modelo"
java -ea -cp "$classes_dir" recetario.tests.ModeloTest

echo "[3/6] Ejecutando pruebas de integración del menú"
java -ea -cp "$classes_dir" recetario.tests.MenuIntegrationTest

echo "[4/6] Verificando reglas de acceso"
bash "$project_root/scripts/test-access.sh"

echo "[5/6] Ejecutando recorrido visible de las opciones 1 a 8"
demo_output="$work_dir/menu-completo.out"
java -Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8  -cp "$classes_dir" recetario.app.Main \
    < "$project_root/tests/fixtures/menu-completo.in" > "$demo_output"

grep -Fq "Primero debe registrar la receta y sus ingredientes" "$demo_output"
grep -Fq "¡Receta y productos registrados exitosamente!" "$demo_output"
grep -Fq "Receta: Arroz con pollo" "$demo_output"
grep -Fq "Costo total de la receta: B/.10.50" "$demo_output"
grep -Fq "Ingrediente 1: B/.1.38" "$demo_output"
grep -Fq "Receta especializada registrada" "$demo_output"
grep -Fq "Responsable: Ana" "$demo_output"
grep -Fq "Especialidad: Pastelería" "$demo_output"
grep -Fq "Responsable: Luis" "$demo_output"
grep -Fq "Carrera: Sistemas" "$demo_output"
grep -Fq "Acompañamiento: Ensalada" "$demo_output"
grep -Fq "this.porciones: 4" "$demo_output"
grep -Fq "Opción inválida. Seleccione una opción" "$demo_output"
grep -Fq "Programa finalizado" "$demo_output"
echo "Recorrido del menú: 14 resultados visibles confirmados"

echo "[6/6] Comprobando conservación visible después de un registro fallido"
failure_output="$work_dir/registro-fallido.out"
java -Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8  -cp "$classes_dir" recetario.app.Main \
    < "$project_root/tests/fixtures/registro-fallido.in" > "$failure_output"
grep -Fq "Error: debe ingresar un número válido." "$failure_output"
grep -Fq "Receta: Receta anterior" "$failure_output"
grep -Fq "Descripción: Anterior 1" "$failure_output"
if grep -Fq "Primero debe registrar la receta y sus ingredientes" "$failure_output"; then
    echo "ERROR: el registro anterior se perdió después del fallo" >&2
    exit 1
fi
echo "Conservación de estado: receta e ingredientes anteriores confirmados"

java -ea -cp "$classes_dir" recetario.tests.TallerStaticFinalTest
java -ea -cp "$classes_dir" recetario.tests.Laboratorio8Test
bash "$project_root/scripts/test-abstract.sh"

echo "RESULTADO: todas las verificaciones finalizaron correctamente"
