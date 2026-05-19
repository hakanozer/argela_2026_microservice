# Kubernetes kurulum adimlari

Bu klasor, projeyi Kubernetes ortaminda calistirmak icin gerekli manifestleri ve yardimci komutlari icerir.

## 1) Docker image olustur

```zsh
cd '/Users/hakan/Documents/GitHub/argela_2026_microservice'
chmod +x k8s/build-images.sh
./k8s/build-images.sh
```

## 2) Namespace ve altyapiyi kur

```zsh
cd '/Users/hakan/Documents/GitHub/argela_2026_microservice'
kubectl apply -f k8s/namespace.yaml
kubectl apply -f k8s/infrastructure.yaml
```

Eger `pull access denied for argela/...` veya `ImagePullBackOff` hatasi gorursen:

```zsh
cd '/Users/hakan/Documents/GitHub/argela_2026_microservice'
./k8s/build-images.sh
kubectl -n argela-ms delete pod -l app=api-gateway
kubectl -n argela-ms delete pod -l app=inventory-service
kubectl -n argela-ms delete pod -l app=order-service
kubectl -n argela-ms delete pod -l app=payment-service
kubectl -n argela-ms delete pod -l app=notification-service
```

## 3) Mikroservisleri kur

```zsh
cd '/Users/hakan/Documents/GitHub/argela_2026_microservice'
kubectl apply -f k8s/microservices.yaml
```

## 4) ApiGateway uzerinden erisim (8080)

Local makineden ApiGateway'e baglanmak icin port-forward kullan:

```zsh
kubectl -n argela-ms port-forward svc/api-gateway 8080:8080
```

Sonra endpointler:

- Inventory: `http://localhost:8080/inventory/list`
- Order: `http://localhost:8080/order/orderList`
- Payment: `http://localhost:8080/payment/...`
- Notification: `http://localhost:8080/notification/...`

## 5) Kubernetes Dashboard kurulumu

Resmi dokumana gore dashboard kurulumu:

```zsh
kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.7.0/aio/deploy/recommended.yaml
kubectl apply -f k8s/dashboard-admin-user.yaml
kubectl -n kubernetes-dashboard create token admin-user

# Service adina gore dogru port-forward komutunu sec
kubectl -n kubernetes-dashboard get svc | cat
# Cogu kurulumda:
kubectl -n kubernetes-dashboard port-forward svc/kubernetes-dashboard 8443:443
# Bazi yeni dagitimlarda:
# kubectl -n kubernetes-dashboard port-forward svc/kubernetes-dashboard-kong-proxy 8443:443
```

Tarayicida ac:

- `https://localhost:8443`

Olusan token ile login ol.

## 6) Servis kayit (service registry) notu

Bu projede Kubernetes Service kaynaklari registry gorevini gorur.
Pod'lar birbirine DNS adi ile erisir:

- `inventory-service.argela-ms.svc.cluster.local`
- `order-service.argela-ms.svc.cluster.local`
- `payment-service.argela-ms.svc.cluster.local`
- `notification-service.argela-ms.svc.cluster.local`
- `redis.argela-ms.svc.cluster.local`
- `kafka.argela-ms.svc.cluster.local`
- `elasticsearch.argela-ms.svc.cluster.local`

