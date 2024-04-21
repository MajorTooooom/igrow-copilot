#!/bin/bash

docker rmi $(docker images --filter "reference=com/dororo/future/igrowcopilot/*:*" -q)
docker images