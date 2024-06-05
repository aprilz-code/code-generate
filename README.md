Mybatis-plus代码生成器  
代码地址 ： https://github.com/aprilz-code/tiny.git  
代码生成器地址：https://github.com/aprilz-code/code-generate.git

cd /src/main/resources  
docker build -t code_gengrate:latest .  
docker-compose -f docker-compose.yml down  
docker rmi $(docker images | grep "none" | awk '{print $3}')  
docker-compose -f docker-compose.yml up -d  
