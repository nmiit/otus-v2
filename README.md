# Тестовый сервис для запуска приложения в Docker и k8s с подключением к Postgresql

Запуск dev БД в docker-compose.dev
```
$ docker-compose -f docker-compose.dev.yml up -d
```
Запуск prod приложения и БД средствами minikube
```
$ minikube start --driver=docker
- запуск minikube 
(если запускается с ошибками, то перед стартом выполнить $ minikube delete --all --purge)

$ minikube addons list
- проверяем в аддонах, что ingress выключен.

$ minikube addons disable ingress
- выключаем, если включен

$ kubectl create secret generic grafana-basic-auth --from-file=auth
- создаём секрет для авторизации в Grafana 
------------------------------------------------------------------------------------------------------------------
$ cd test_kuber_chart
- переходим в папку test_kuber_chart

$ helm dep build
- билдим зависимости helm чарта (ingress-nginx и prometheus сервер)

$ helm install myapp . -f values.yaml -f values-secret.yaml
- запускаем чарт

$ helm uninstall myapp
- удаляем запущенный чарт, если нужно

# проверка
$ kubectl get pods
$ kubectl get pvc
$ kubectl get pv
$ kubectl get svc
$ kubectl get rs
$ kubectl get ingress
$ kubectl get endpoints myapp-grafana

$ kubectl logs deployment/myapp-test-kuber-chart
- логи приложения
------------------------------------------------------------------------------------------------------------------
(для windows) добавь в etc\hosts
127.0.0.1 testkuber
и
127.0.0.1 testkuber.grafana
```
### Для подключения к БД извне
```
# сначала находим имя поды postgresql
$ kubectl get pods  

# после прокидываем порты на пк
$ kubectl port-forward postgres-0 5432:5432
```
Консоль будет активна, и пока мы её не закроем, или не выйдем из активного режима,
к БД можно подключиться средствами IntellijIdea или DBeaver например.

### Spring profiles
local - запускаем с этим профилем приложение локально

------------------------------------------------------------------------------------------------------------------

### Хосты кластера

хост для входа в prometheus    - http://testkuber/actuator/prometheus
хост для входа в графану    - http://testkuber.grafana
(логин/пароль для доступа - admin/password123 (auth), логин/пароль для входа - admin/prom-operator (values.yaml))

------------------------------------------------------------------------------------------------------------------

### Запросы для теста / картинка архитектуры взаимодействия

файл rest_test.json 
файл взаимодействие.png

Namespace задавать не нужно. Всё ставится в default.