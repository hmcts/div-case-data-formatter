FROM openjdk:8-jre-alpine

COPY build/install/div-case-formatter-service /opt/app/

WORKDIR /opt/app

HEALTHCHECK --interval=100s --timeout=100s --retries=10 CMD http_proxy="" wget -q http://localhost:4011/health || exit 1

EXPOSE 4009

ENTRYPOINT ["/opt/app/bin/div-case-formatter-service"]
