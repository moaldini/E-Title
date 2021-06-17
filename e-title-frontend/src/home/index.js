import { Link } from 'react-router-dom'
import { 
  Row, 
  Col 
} from 'reactstrap'
import logo from '@src/assets/images/logo.png'
import img from '@src/assets/images/illustration/pricing-Illustration.svg'
import '@styles/base/pages/page-auth.scss'
import '@styles/react/pages/page-profile.scss'

const Home = () => {
  return (
    <div className='auth-wrapper auth-v2'>
      <Row className='auth-inner m-0'>
        <Col className='d-flex align-items-center auth-bg px-2 p-lg-5' lg='12' sm='12'>
          <Col className='px-xl-2 mx-auto' sm='8' md='6' lg='12'>
            <div className='misc-inner p-2 p-sm-3'>
              <div className='w-100 text-center'>
                <div className=" mb-2 pb-3 w-100 text-center">
                  <Link to='/'>
                    <img className='img-fluid' src={logo} alt='' />
                  </Link>
                </div>
                <div className=" mb-2 pb-3 w-100 text-center">
                <Link color='primary' to='/login'>
                  <span className='btn btn-primary'>Login / Register</span>
                </Link>
                </div>
                <h2 className='mb-1'>Under Maintenance 🛠</h2>
                <p className='mb-3'>Sorry for the inconvenience but we're performing some maintenance at the moment</p>
                <img className='img-fluid' src={img} alt='' />
              </div>
            </div>
          </Col>
        </Col>
      </Row>
    </div>
  
  )
}

export default Home