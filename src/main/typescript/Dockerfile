FROM node:20-alpine3.19
WORKDIR /app
COPY package.json package.json
RUN npm install
COPY . .
RUN npm build

FROM nginx:stable-alpine3.17
COPY --from=build /app/dist /opt/site
COPY nginx.conf /etc/nginx/nginx.conf
