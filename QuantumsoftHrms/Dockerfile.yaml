FROM nginx:alpine
COPY dist/quantumsoft-hrms/browser /usr/share/nginx/html
COPY default.conf /etc/nginx/conf.d/default.conf
RUN chmod -R 755 /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
