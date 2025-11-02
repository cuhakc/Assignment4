#!/bin/bash
mkdir -p results
for f in data/*.json; do
  name=$(basename "$f" .json)
  echo "Running dataset: $name"

  mvn -q exec:java -Dexec.mainClass="graph.Main" -Dexec.args="$f"
  if [ -f tasks_output.json ]; then
    mv tasks_output.json results/${name}_output.json
    echo "Saved results/${name}_output.json"
  else
    echo "No output produced for $name"
  fi
done

echo
echo "All datasets processed successfully!"
echo "Results are saved in the 'results/' directory."
