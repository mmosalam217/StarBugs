FROM node:12.2.0-alpine as build

WORKDIR /app

COPY package.json /app

RUN npm install
COPY . /app
RUN npm run build --prod


FROM nginx:1.16.0-alpine

COPY starbugsweb.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist/StarBugsWeb /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]