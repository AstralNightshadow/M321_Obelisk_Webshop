# Obelix & Co. Webshop

The webshop keeps its public API while delegating menhir storage to the Quarry service and basket state/valuation to the Basket service. Service URLs are configurable through `obelix.clients.quarry-url` and `obelix.clients.basket-url` (or the matching `OBELIX_CLIENTS_*_URL` environment variables).

## Services

| Service | Gradle module | Port | Responsibility |
| --- | --- | ---: | --- |
| Webshop | `obelix-webshop` | 8080 | Backwards-compatible public API and orchestration |
| Quarry | `obelix-quarry-impl` | 8081 | Menhir persistence and inventory gauge |
| Basket | `obelix-basket` | 8082 | Basket state and offer valuation |
| Shared API | `obelix-quarry-api` | n/a | Quarry HTTP contract and menhir DTOs |
| Common | `obelix-common` | n/a | Shared Basket contract, configuration, metrics and tracing dependencies |

The public webshop routes remain `/api`, `/api/menhirs`, `/api/menhirs/{menhirId}`, `/api/quarry/{menhirId}`, `/api/basket/offer`, `/api/basket`, and `/api/basket/buy/{menhirId}`.

## Run With Docker Compose

Prerequisites: Java 25, Docker with BuildKit/buildpacks support, and a running Docker daemon.

Build the three service images, then start the stack:

```powershell
.\gradlew.bat bootBuildImage
docker compose up -d
```

The webshop is at `http://localhost:8080`; Zipkin is at `http://localhost:9411`; Prometheus is at `http://localhost:9090`; and Grafana is at `http://localhost:3000` (login `admin` / `admin`). Grafana provisions Prometheus automatically as its default data source. In Grafana, open **Explore**, choose the Prometheus data source, and query `obelix_hinkelsteine` to see the current quarry inventory. The metric is scraped every 15 seconds.

Use the Zipkin UI to inspect distributed request traces. The `webshop.menhir.exchange`, `quarry.menhir.*`, and `basket.offer.evaluate` observations create custom spans, and WebClient propagates trace context between services. Sampling is set to 100% for this demonstration.

## Verify

Run the tests with:

```powershell
.\gradlew.bat test
```

The public webshop API remains the client-facing entry point; the direct Quarry and Basket ports are exposed for service inspection and local development.
