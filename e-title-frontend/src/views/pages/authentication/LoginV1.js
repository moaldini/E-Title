import { useState, useContext, Fragment } from 'react'
import { useSkin } from '@hooks/useSkin'
import { AbilityContext } from '@src/utility/context/Can'
import { useDispatch } from 'react-redux'
import InputPasswordToggle from '@components/input-password-toggle'
import { Link, useHistory } from 'react-router-dom'
import { useForm } from 'react-hook-form'
import { toast, Slide } from 'react-toastify'
import { handleLogin } from '@store/actions/auth'
import classnames from 'classnames'
import Avatar from '@components/avatar'
import { Alert, Card, CardBody, CardTitle, CardText, Form, FormGroup, Label, Input, CustomInput, Button } from 'reactstrap'
import '@styles/base/pages/page-auth.scss'
import themeConfig from '@configs/themeConfig'
import { getHomeRouteForLoggedInUser, isObjEmpty } from '@utils'
import useJwt from '@src/auth/jwt/useJwt'
import { Coffee } from 'react-feather'

const ToastContent = ({ name, role }) => (
  <Fragment>
    <div className='toastify-header'>
      <div className='title-wrapper'>
        <Avatar size='sm' color='success' icon={<Coffee size={12} />} />
        <h6 className='toast-title font-weight-bold'>Welcome, {name}</h6>
      </div>
    </div>
    <div className='toastify-body'>
      <span>You have successfully logged in as an {role} user to. Now you can start to explore. Enjoy!</span>
    </div>
  </Fragment>
)

const LoginV1 = () => {
  const [skin, setSkin] = useSkin()
  const ability = useContext(AbilityContext)
  const dispatch = useDispatch()
  const history = useHistory()
  const [valErrors, setValErrors] = useState({})
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')

  const { register, errors, handleSubmit } = useForm()
  //skin === 'dark' ? 'login-v2-dark.svg' : 'login-v2.svg',
  const illustration = 'plate.gif',
    source = require(`@src/assets/images/pages/${illustration}`).default,
    logo  = require(`@src/assets/images/logo.png`).default

  const onSubmit = data => {
    if (isObjEmpty(errors)) {
      useJwt
        .login({ email, password })
        .then(res => {
                const data = { ...res.data.userData, accessToken: res.data.accessToken, refreshToken: res.data.accessToken }
          dispatch(handleLogin(data))
          ability.update(res.data.userData.ability)
          history.push(getHomeRouteForLoggedInUser(data.role))
          toast.success(
            <ToastContent name={data.fullName || data.username} role={data.role} />,
            { transition: Slide, hideProgressBar: true, autoClose: 2000 }
          )
        })
        .catch(res => {
          const arr = {}
          for (const property in res.response.data) {
            if (res.response.data[property] !== null) arr[property] = res.response.data[property]
          }
          setValErrors(arr)
      })
    }
  }
  return (
    <div className='auth-wrapper auth-v1 px-2'>
      <div className='auth-inner py-2'>
        <Card className='mb-0'>
          <CardBody>
            <Link className='brand-logo text-center' to='/' onClick={e => e.preventDefault()}>
              <h2 className='brand-text text-primary ml-1'>Welcome to {themeConfig.app.appName}</h2>
            </Link>
            <CardTitle tag='h4' className='mb-1'></CardTitle>
            <CardText className='mb-2 text-center'>
                Please sign-in to your account and start
            </CardText>
            {Object.keys(valErrors).length && valErrors.error ? (
              <Alert color='danger m-1'>
                <div className='alert-body text-center'>Error: {valErrors.error}</div>
              </Alert>
              ) : null}
            <Form className='auth-login-form mt-2' onSubmit={handleSubmit(onSubmit)}>
              <FormGroup>
                <Label className='form-label' for='login-email'>
                  Email
                </Label>
                <Input
                  autoFocus
                  type='email'
                  value={email}
                  id='login-email'
                  name='login-email'
                  placeholder='john@example.com'
                  onChange={e => setEmail(e.target.value)}
                  className={classnames({ 'is-invalid': errors['login-email'] })}
                  innerRef={register({ required: true, validate: value => value !== '' })}
                />
              </FormGroup>
              <FormGroup>
                <div className='d-flex justify-content-between'>
                  <Label className='form-label' for='login-password'>
                    Password
                  </Label>
                  <Link to='/forgot-password' className="hidden">
                    <small>Forgot Password?</small>
                  </Link>
                </div>
                <InputPasswordToggle
                  value={password}
                  id='login-password'
                  name='login-password'
                  className='input-group-merge'
                  onChange={e => setPassword(e.target.value)}
                  className={classnames({ 'is-invalid': errors['login-password'] })}
                  innerRef={register({ required: true, validate: value => value !== '' })}
                />
              </FormGroup>
              <FormGroup>
                <CustomInput type='checkbox' className='custom-control-Primary' id='remember-me' label='Remember Me' />
              </FormGroup>
              <Button.Ripple type='submit' color='primary' block>Sign in</Button.Ripple>
            </Form>
            
            <p className='text-center mt-2'>
              <span className='mr-25'>New on our platform?</span>
              <Link to='/register'><span>Create an account</span></Link>
            </p>
          </CardBody>
        </Card>
      </div>
    </div>
  )
}

export default LoginV1
