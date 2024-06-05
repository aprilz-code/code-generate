Mybatis-plus代码生成器

docker build -t  registry.cn-shenzhen.aliyuncs.com/whiteblog/code_gengrate:sup .  
docker push  registry.cn-shenzhen.aliyuncs.com/whiteblog/code_gengrate:sup .

docker run -d --name code_gengrate -p 8085:8085 registry.cn-shenzhen.aliyuncs.com/whiteblog/code_gengrate:sup registry.cn-shenzhen.aliyuncs.com/whiteblog/code_gengrate:security

cd /src/main/resources  
docker build -t code_gengrate:latest .  
docker-compose -f docker-compose.yml down  
docker rmi $(docker images | grep "none" | awk '{print $3}')  
docker-compose -f docker-compose.yml up -d  
