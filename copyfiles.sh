[[ -z "$2" ]] && name="*java" || name="$2"
[[ -z "$3" ]] && destination=./target || destination="$3"

mkdir "$destination"

for file in $(find "$1" -name "$name")
do
  if [ -f "$file" ] ; then
    cp "$file" "$destination"
  fi
done