#!/bin/bash


cd ../
workDir=$(pwd)
echo "当前工作目录：$workDir"


# 提示用户输入Y/N
read -p "是否删除当前工作目录下所有内容? (Y/N): " input

# 判断用户输入并执行相应操作
if [ "$input" == "Y" ] || [ "$input" == "y" ]; then
  echo "You chose to proceed."
  # 在这里添加你希望执行的操作
  rm -rf ./*
  rm -rf ./.env
elif [ "$input" == "N" ] || [ "$input" == "n" ]; then
  echo "You chose not to proceed."
  # 在这里添加你希望执行的操作
else
  echo "invalid input. please enter Y or N."
fi
