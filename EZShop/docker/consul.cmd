#创建自定义网络
docker network create --subnet=172.18.0.0/16 mynetwork

#执行consul服务器
docker run -itd --network mynetwork --ip 172.18.0.11 --name consul-server1 -p 8310:8300 -p 8311:8301 -p 8311:8301/udp -p 8312:8302/udp -p 8312:8302 -p 8410:8400 -p 8510:8500 -p 8610:8600/udp ezshop-consul:v1.0 ./consul agent -server -bind=172.18.0.11 -client=0.0.0.0 -bootstrap-expect=1 -data-dir=/opt/consul/consul_data/ -node=server1 -ui
docker run -itd --network mynetwork --ip 172.18.0.12 --name consul-server2 -p 8320:8300 -p 8321:8301 -p 8321:8301/udp -p 8322:8302/udp -p 8322:8302 -p 8420:8400 -p 8520:8500 -p 8620:8600/udp ezshop-consul:v1.0 ./consul agent -server -bind=172.18.0.12 -client=0.0.0.0 -bootstrap-expect=1 -data-dir=/opt/consul/consul_data/ -node=server2 -ui -join=172.18.0.11
docker run -itd --network mynetwork --ip 172.18.0.13 --name consul-server3 -p 8330:8300 -p 8331:8301 -p 8331:8301/udp -p 8332:8302/udp -p 8332:8302 -p 8430:8400 -p 8530:8500 -p 8630:8600/udp ezshop-consul:v1.0 ./consul agent -server -bind=172.18.0.13 -client=0.0.0.0 -bootstrap-expect=1 -data-dir=/opt/consul/consul_data/ -node=server3 -ui -join=172.18.0.11
