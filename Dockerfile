ARG APP_INSIGHTS_AGENT_VERSION=2.5.1

FROM hmctspublic.azurecr.io/base/java:11-distroless

COPY lib/AI-Agent.xml /opt/app/
COPY build/libs/div-case-formatter-service.jar /opt/app/

EXPOSE 4011

CMD ["div-case-formatter-service.jar"]
