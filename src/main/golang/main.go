package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"net/http"
	"os"
	"strings"
)

func main() {
	router := gin.Default()
	router.GET("/oauth2/callback", callback)
	router.GET("/oauth2/google", googleOAuth)

	err := router.Run("localhost:8780")
	if err != nil {
		return
	}
}

func callback(c *gin.Context) {
	authCode := c.Query("code")
	apiUrl := os.Getenv("API_DOMAIN") + "/oauth2/callback?code=" + authCode

	res, err := http.Get(apiUrl)

	if err != nil {
		panic(err)
	}

	setCookies := res.Header.Get("Set-Cookie")
	cookieValue := ""

	cookieParts := strings.Split(setCookies, ";")
	for _, part := range cookieParts {
		if strings.HasPrefix(strings.TrimSpace(part), "jwt=") {
			cookieValue = strings.TrimPrefix(strings.TrimSpace(part), "jwt=")
			break
		}
	}

	cookie := &http.Cookie{
		Name:  "jwt",
		Value: cookieValue,
		Path:  "/",
	}

	http.SetCookie(c.Writer, cookie)
	c.Redirect(http.StatusMovedPermanently, os.Getenv("FRONTEND_DOMAIN"))
}

func googleOAuth(c *gin.Context) {
	c.Redirect(http.StatusMovedPermanently, getAuthUrl())
}

func getAuthUrl() string {
	endpoint := "https://accounts.google.com/o/oauth2/v2/auth"
	scope := "email profile"
	clientId := os.Getenv("GOOGLE_CLIENT_ID")
	redirectUri := os.Getenv("REDIRECT_DOMAIN") + "/oauth2/callback"

	return fmt.Sprintf("%s?scope=%s&client_id=%s&redirect_uri=%s&response_type=code", endpoint, scope, clientId, redirectUri)
}
