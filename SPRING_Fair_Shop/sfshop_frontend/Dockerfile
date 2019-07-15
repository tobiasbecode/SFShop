# stage 1
FROM node:latest as node
WORKDIR /app
COPY . .
RUN npm install
RUN npm install bootstrap
RUN npm run build --prod

# stage 2
FROM nginx:1.14.0-alpine

RUN apk --no-cache add \
      python2 \
      py2-pip && \
    pip2 install j2cli[yaml]

RUN apk add --update bash && rm -rf /var/cache/apk/*

RUN rm -rf /usr/share/nginx/html/*

COPY --from=node /app/dist/spring5webshop /usr/share/nginx/html

COPY nginx.conf.j2 /templates/

COPY docker-entrypoint.sh /

ENTRYPOINT ["sh", "/docker-entrypoint.sh"]

CMD ["nginx", "-g", "daemon off;"]