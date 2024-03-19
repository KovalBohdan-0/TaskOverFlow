FROM node:20-alpine as build
WORKDIR /app
COPY package.json package.json
#RUN npm install
COPY . .
RUN npm run build-dev

FROM nginx:stable-alpine3.17
COPY --from=build /app/dist /opt/site
COPY nginx.conf /etc/nginx/nginx.conf
