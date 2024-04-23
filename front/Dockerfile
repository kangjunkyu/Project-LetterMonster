FROM node:16 AS builder
COPY package.json .
RUN npm install
RUN npm install --save-dev @babel/plugin-proposal-private-property-in-object --legacy-peer-deps

COPY . .
RUN CI=false npm run build

# nginx 이미지를 사용합니다. 뒤에 tag가 없으면 latest 를 사용.
FROM nginx

# host pc의 현재경로의 build 폴더를 workdir 의 build 폴더로 복사
COPY --from=builder build /usr/share/nginx/html

# nginx 의 default.conf 를 삭제
RUN rm /etc/nginx/conf.d/default.conf

# host pc 의 nginx.conf 를 아래 경로에 복사
COPY ./nginx.conf /etc/nginx/conf.d

# 80 포트 오픈
EXPOSE 80

# container 실행 시 자동으로 실행할 command. nginx 시작함
CMD ["nginx", "-g", "daemon off;"]