import { Fragment, useContext, useRef, useState } from 'react'
import { Link, useHistory } from 'react-router-dom'
import Select from 'react-select'
import classnames from 'classnames'
import { handleLogin } from '@store/actions/auth'
import useJwt from '@src/auth/jwt/useJwt'
import { useDispatch } from 'react-redux'
import { getHomeRouteForLoggedInUser, selectThemeColors, isObjEmpty } from '@utils'
import InputPasswordToggle from '@components/input-password-toggle'
import { Row, Col, Card, CardBody, CardTitle, CardText, Form, FormGroup, Label, Input, CustomInput, Button } from 'reactstrap'
import '@styles/base/pages/page-auth.scss'
import { APIs } from '@src/utility/context/API'
import { useForm } from 'react-hook-form'
//import themeConfig from '@configs/themeConfig'
import { AbilityContext } from '@src/utility/context/Can'
import {
  AvForm,
  AvGroup,
  AvField,
  AvInput,
  AvFeedback,
  AvRadioGroup,
  AvCheckboxGroup,
  AvRadio,
  AvCheckbox
} from 'availity-reactstrap-validation-safe'
import '@styles/react/libs/flatpickr/flatpickr.scss'

const RegisterV1 = () => {
  const { login, registeration} = useContext(APIs)
  const [values, setState] = useState()
  const history = useHistory()
  const { register, errors, handleSubmit } = useForm()
  const [valErrors, setValErrors] = useState({})
  const ability = useContext(AbilityContext)
  const dispatch = useDispatch()
  const countyOptions = [
    { value: 'Jefferson', label: 'Jefferson' },
    { value: 'Johnson', label: 'Johnson' },
    { value: 'Jones', label: 'Jones' },
    { value: 'Iowa', label: 'Iowa' }
  ]
  
  const RememberMe = () => {
    return (
      <Fragment>
      I agree to <a className='ml-25' href='/' onClick={e => e.preventDefault()}>privacy policy & terms</a>
      </Fragment>
    )
  }

  const handleSubmitForm = (event, errors, values) => {
    if (isObjEmpty(errors)) {
      debugger
      setState({errors, values})
      const data = { 
        firstName: values.firstName, 
        lastName: values.lastName, 
        email: values.email,
        password: values.password,
        ssn: values.ssn,
        mobile: '+123456789',
        address: {
            addressLine1: values.ave,
            city: values.city,
            state: values.state,
            country: values.county,
            zipcode: values.zip
        }
      }
      useJwt
        .register(data)
        .then(res => {
            const dataLogin = { ...res.data.userData, accessToken: res.data.accessToken, refreshToken: res.data.accessToken }
            dispatch(handleLogin(dataLogin))
            ability.update(res.data.userData.ability)
            history.push(getHomeRouteForLoggedInUser(dataLogin.role))
          
        })
        .catch(res => {
            const arr = {}
            for (const property in res.response.data) {
              if (res.response.data[property] !== null) arr[property] = res.response.data[property]
            }
            setValErrors(arr)
            //if (res.data.error.email !== null) console.error(res.data.error.email)
            //if (res.data.error.username !== null) console.error(res.data.error.username)
        })
    }
  }

  return (
    <div className='auth-wrapper auth-v1 px-2'>
      <div className='auth-inner py-2'>
        <Card className='mb-0'>
          <CardBody>
            <div className='brand-logo'>
              <h2 className='brand-text text-primary ml-1'>Create An Account</h2>
            </div>
            <CardTitle tag='h4' className='mb-1'></CardTitle>
            <CardText className='mb-2 text-center'>Make your app management easy and fun!</CardText>
            <AvForm method='post' className='auth-register-form mt-2' onSubmit={handleSubmitForm}>
            <Row className='m-0'>
              <Col lg='6' sm='12'>
                <AvGroup>
                  <Label className='form-label' for='register-first-name'>First Name</Label>
                  <AvInput
                    type='text'
                    id='register-first-name'
                    name={'firstName'}
                    placeholder=''
                    className={classnames({ 'is-invalid': errors['register-first-name'] })}
                    innerRef={register({ required: true, validate: value => value !== '' })}
                    autoFocus
                    required
                  />
                  <AvFeedback>Please enter a valid name!</AvFeedback>
                </AvGroup>
              </Col>
              <Col lg='6' sm='12'>
                <AvGroup>
                  <Label className='form-label' for='register-last-name'>Last Name</Label>
                  <AvInput
                    type='text'
                    id='register-last-name'
                    name={'lastName'}
                    placeholder=''
                    className={classnames({ 'is-invalid': errors['register-last-name'] })}
                    innerRef={register({ required: true, validate: value => value !== '' })}
                    required
                  />
                  <AvFeedback>Please enter a valid name!</AvFeedback>
                </AvGroup>
              </Col>
            </Row>
            <Row className='m-0'>
                <Col lg='6' sm='12'>
                  <AvGroup>
                    <Label className='form-label' for='register-email'>Email</Label>
                    <AvInput
                        type='email'
                        id='register-email'
                        name='email'
                        placeholder=''
                        className={classnames({ 'is-invalid': errors['register-email'] })}
                        innerRef={register({ required: true, validate: value => value !== '' })}
                        required
                    />
                    {Object.keys(valErrors).length && valErrors.email ? (
                      <small className='text-danger'>{valErrors.email}</small>
                    ) : null}
                    <AvFeedback>Please enter a valid name!</AvFeedback>
                  </AvGroup>
                </Col>
                <Col lg='6' sm='12'>
                  <AvGroup>
                    <Label className='form-label' for='register-password'>Password</Label>
                    <AvInput
                        type="password"
                        id='register-password'
                        name='password'
                        placeholder=''
                        className={classnames({ 'is-invalid input-group-merge': errors['register-password']})}
                        required
                    />
                    <AvFeedback>Please enter a valid name!</AvFeedback>
                  </AvGroup>
                </Col>
            </Row>
            <Row className='m-0'>
              <Col lg='6' sm='12'>
                <AvGroup>
                  <Label className='form-label' for='register-ssn'>SSN</Label>
                  <AvInput
                      type='text'
                      
                      id='register-ssn'
                      name='ssn'
                      placeholder=''
                      className={classnames({ 'is-invalid': errors['register-ssn'] })}
                      innerRef={register({ required: true, validate: value => value !== '' })}
                      required
                  />
                  <AvFeedback>Please enter a valid SSN!</AvFeedback>
                </AvGroup>
              </Col>
              <Col lg='6' sm='12'>
                <AvGroup>
                  <Label className='form-label' for='register-zip'>Zip</Label>
                  <AvInput
                      type='text'
                      
                      id='register-zip'
                      name='zip'
                      placeholder=''
                      className={classnames({ 'is-invalid': errors['register-zip'] })}
                      innerRef={register({ required: true, validate: value => value !== '' })}
                      required
                  />
                  <AvFeedback>Please enter a valid Zip!</AvFeedback>
                </AvGroup>
              </Col>
            </Row>
            <Row className='m-0'>
              <Col lg='6' sm='12'>
                <AvGroup>
                  <Label className='form-label' for='register-state'>State</Label>
                  <AvInput
                      type='text'
                      id='register-state'
                      name='state'
                      placeholder=''
                      className={classnames({ 'is-invalid': errors['register-state'] })}
                      innerRef={register({ required: true, validate: value => value !== '' })}
                      required
                  />
                  <AvFeedback>Please enter a valid State!</AvFeedback>
                </AvGroup>
              </Col>
              <Col lg='6' sm='12'>
                <AvGroup>
                  <Label className='form-label' for='register-city'>City</Label>
                  <AvInput
                      type='text'
                      id='register-city'
                      name='city'
                      placeholder=''
                      className={classnames({ 'is-invalid': errors['register-city'] })}
                      innerRef={register({ required: true, validate: value => value !== '' })}
                      required
                  />
                </AvGroup>
              </Col>
            </Row>
            <Row className='m-0 mb-2'>
              <Col lg='6' sm='12'>
                <AvGroup>
                  <Label className='form-label' for='register-county'>County</Label>
                  <Select
                    id="register-county"
                    name="county"
                    theme={selectThemeColors}
                    className='react-select'
                    classNamePrefix='select'
                    defaultValue={countyOptions[0]}
                    options={countyOptions}
                    isClearable={false}
                  />
                </AvGroup>
              </Col>
              <Col lg='6' sm='12'>
                <AvGroup>
                  <Label className='form-label' for='register-ave'>Ave</Label>
                  <AvInput
                      type='text'
                      
                      id='register-ave'
                      name='ave'
                      placeholder=''
                      className={classnames({ 'is-invalid': errors['register-ave'] })}
                      innerRef={register({ required: true, validate: value => value !== '' })}
                      required
                  />
                </AvGroup>
              </Col>
            </Row>
            
            <Row className='m-0'>
              <Col lg='12'>
                <Button.Ripple type='submit' color='primary'block>Sign up</Button.Ripple>
              </Col>
            </Row>
            </AvForm>
            <p className='text-center mt-2'>
              <span className='mr-25'>Already have an account?</span>
              <Link to='/login'>
                <span>Sign in instead</span>
              </Link>
            </p>
          </CardBody>
        </Card>
      </div>
    </div>
  )
  /*
  <Row className='m-0 mt-2'>
    <Col lg='12' sm='12'>
      <FormGroup>
        <CustomInput
          type='checkbox'
          className='custom-control-Primary'
          id='remember-me'
          label={<RememberMe />}
        />
      </FormGroup>
    </Col>
  </Row>
  */
}

export default RegisterV1
