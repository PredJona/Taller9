#!/usr/bin/env bash
set -euo pipefail
project_root=$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd -P)
work_dir=$(mktemp -d /tmp/lab8-abstract.XXXXXX)
trap 'rm -r "$work_dir"' EXIT
mapfile -t sources < <(find "$project_root/src" -name '*.java' -print | sort)
javac --release 21 -encoding UTF-8 -d "$work_dir" "${sources[@]}"
# La instrucción inválida sólo existe en un archivo temporal de prueba.
cat > "$work_dir/InstanciaInvalida.java" <<'JAVA'
import recetario.modelo.Persona;
class InstanciaInvalida {
    Persona persona = new Persona("Ana", "ana@ejemplo.com");
}
JAVA
if LC_ALL=C javac -cp "$work_dir" -d "$work_dir" "$work_dir/InstanciaInvalida.java" 2> "$work_dir/error"; then
    echo 'ERROR: Persona permitió una instancia directa' >&2
    exit 1
fi
grep -q 'abstract; cannot be instantiated' "$work_dir/error"
echo 'Persona abstracta: instanciación directa rechazada por el compilador'
