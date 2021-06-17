// ** Core JWT Import
import useJwt from '@src/@core/auth/jwt/useJwt'
const jwtConfig = {
    loginEndpoint: 'http://localhost:8888/authenticate/login',
    registerEndpoint: 'http://localhost:8888/authenticate/register',
    refreshEndpoint: '/jwt/refresh-token',
    logoutEndpoint: '/jwt/logout'
}  
const { jwt } = useJwt(jwtConfig)

export default jwt
