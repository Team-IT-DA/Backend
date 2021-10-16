#!/usr/bin/env bash

docker run -d --rm --name itda-production \
  -e MYSQL_ROOT_PASSWORD=1234 \
  -e MYSQL_ROOT_HOST='%' \
  -e MYSQL_DATABASE=itda \
  -e TZ=Asia/Seoul \
  -p 3306:3306 mysql:5.7 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci --sql_mode=""