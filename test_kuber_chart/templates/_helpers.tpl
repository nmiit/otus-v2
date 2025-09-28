{{- define "spring-app.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" }}
{{- end }}

{{- define "spring-app.fullname" -}}
{{- if .Values.fullnameOverride }}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- $name := default .Chart.Name .Values.nameOverride }}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" }}
{{- end }}
{{- end }}

{{- define "spring-app.labels" -}}
app: {{ include "spring-app.fullname" . }}
{{- end }}

{{- define "postgres.fullname" -}}
postgres
{{- end }}

{{/*
Kong plugin name
*/}}
{{- define "spring-app.kongPluginName" -}}
{{- printf "%s-auth-plugin" (include "spring-app.fullname" .) | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Kong ingress configuration name
*/}}
{{- define "spring-app.kongIngressName" -}}
{{- printf "%s-kong-config" (include "spring-app.fullname" .) | trunc 63 | trimSuffix "-" }}
{{- end }}