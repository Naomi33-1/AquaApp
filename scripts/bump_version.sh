#!/usr/bin/env bash
# Увеличивает versionCode на 1 и patch-часть versionName (1.0.0 -> 1.0.1)
set -euo pipefail

FILE="${1:-app/build.gradle.kts}"

code=$(grep -E '^\s*versionCode\s*=' "$FILE" | head -1 | grep -oE '[0-9]+')
name=$(grep -E '^\s*versionName\s*=' "$FILE" | head -1 | grep -oE '"[^"]+"' | tr -d '"')

IFS='.' read -r major minor patch <<< "$name"
new_code=$((code + 1))
new_name="${major}.${minor:-0}.$(( ${patch:-0} + 1 ))"

sed -i -E "s/^(\s*versionCode\s*=\s*)[0-9]+/\1${new_code}/" "$FILE"
sed -i -E "s/^(\s*versionName\s*=\s*)\"[^\"]+\"/\1\"${new_name}\"/" "$FILE"

echo "versionCode: ${code} -> ${new_code}"
echo "versionName: ${name} -> ${new_name}"
