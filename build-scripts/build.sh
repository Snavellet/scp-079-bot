#!/bin/bash

./gradlew build

rm -r ./build/libs

./gradlew jar

jarDirectory="/build/libs"

if [[ ! -d ".$jarDirectory" ]]; then
  mkdir ./build/libs

  echo "Rerun this script again."
else
  cd ".$jarDirectory" || {
    echo "Unknown directory"
    exit 1
  }

  for file in *.jar; do
    mv "$file" "rick-sanchez-bot.jar"
  done

  cd "../../"
  docker-compose up --build -d
fi
