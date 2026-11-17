#!/usr/bin/env bash
set -euo pipefail

project_root=$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd -P)
work_dir=$(mktemp -d /tmp/lab6-access.XXXXXX)
trap 'rm -r "$work_dir"' EXIT

classes_dir="$work_dir/classes"
mkdir -p "$classes_dir"

mapfile -t production_sources < <(find "$project_root/src" -name '*.java' -print | sort)
mapfile -t positive_sources < <(find "$project_root/tests" -name '*.java' -print | sort)

javac --release 21 -encoding UTF-8 -Xlint:all -Werror -d "$classes_dir" \
    "${production_sources[@]}" "${positive_sources[@]}"

java -ea -cp "$classes_dir" recetario.modelo.AccesoMismoPaqueteTest
java -ea -cp "$classes_dir" recetario.modelo.RecetaPostreMismoPaqueteTest
java -ea -cp "$classes_dir" recetario.especialidades.AccesoSubclaseTest

negative_count=0
for source_template in "$project_root"/tests/access-invalid/*.java.txt; do
    filename=$(basename "$source_template" .txt)
    source_file="$work_dir/$filename"
    cp "$source_template" "$source_file"
    diagnostic_file="$work_dir/$filename.diagnostic"

    if LC_ALL=C javac -cp "$classes_dir" -d "$classes_dir" \
            "$source_file" 2>"$diagnostic_file"; then
        echo "ERROR: $filename debía fallar al compilar" >&2
        exit 1
    fi
    if ! grep -Eq 'private access|protected access|cannot be accessed from outside package' \
            "$diagnostic_file"; then
        echo "ERROR: $filename falló por una razón inesperada" >&2
        sed -n '1,12p' "$diagnostic_file" >&2
        exit 1
    fi

    negative_count=$((negative_count + 1))
    echo "$filename: rechazo de acceso confirmado"
done

echo "test-access: 8 accesos permitidos y $negative_count accesos prohibidos verificados"
