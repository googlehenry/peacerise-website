#先把springboot项目打成jar包: gradle bootJar(gradle:build:bootJar)
#gradle bootJar
#因为用到了lcoalhost其他端口,在docker中不能直连,又不愿修改代码,所以这个是跑不起来的.
docker build -t peacerise-website:v0.0.1 .
docker run -p 8080:8080 -d peacerise-website:v0.0.1