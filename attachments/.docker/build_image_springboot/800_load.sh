#!/bin/bash

time1=$(TZ=UTC-8 date "+%Y-%m-%d %H:%M:%S")
echo '-------------------------------------------------[开始load镜像 '${time1}']-------------------------------------------------'

docker save -o com_dororo_future_igrowcopilot_springboot_1.0.0.tar com/dororo/future/igrowcopilot/springboot:1.0.0
docker load --input com_dororo_future_igrowcopilot_springboot_1.0.0.tar

time2=$(TZ=UTC-8 date "+%Y-%m-%d %H:%M:%S")
time3=$(($(date +%s -d "${time2}")-$(date +%s -d "${time1}")));
echo '-------------------------------------------------[结束load镜像 '${time2}']---------------------------------------------------耗时['${time3}']秒'
echo '查找相关镜像...'
echo '--------------------------------------------------------------------------------------------------------------------------------------'
docker images --filter "reference=com/dororo/future/igrowcopilot/*:*"
echo '--------------------------------------------------------------------------------------------------------------------------------------'