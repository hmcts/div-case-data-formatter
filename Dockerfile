ARG APP_INSIGHTS_AGENT_VERSION=3.2.6
ARG PLATFORM=""
FROM hmctspublic.azurecr.io/base/java${PLATFORM}:17-distroless

ENV APP div-case-formatter-service.jar

COPY lib/AI-Agent.xml /opt/app/
COPY build/libs/div-case-formatter-service.jar /opt/app/

EXPOSE 4011

CMD ["div-case-formatter-service.jar"]
