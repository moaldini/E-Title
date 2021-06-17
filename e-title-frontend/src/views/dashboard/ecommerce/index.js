import { useContext, useState, useEffect } from 'react'
import { Row, Col } from 'reactstrap'
import CompanyTable from './CompanyTable'
import { ThemeColors } from '@src/utility/context/ThemeColors'
import Earnings from '@src/views/ui-elements/cards/analytics/Earnings'
import CardMedal from '@src/views/ui-elements/cards/advance/CardMedal'
import CardMeetup from '@src/views/ui-elements/cards/advance/CardMeetup'
import StatsCard from '@src/views/ui-elements/cards/statistics/StatsCard'
import GoalOverview from '@src/views/ui-elements/cards/analytics/GoalOverview'
import RevenueReport from '@src/views/ui-elements/cards/analytics/RevenueReport'
import OrdersBarChart from '@src/views/ui-elements/cards/statistics/OrdersBarChart'
import ProfitLineChart from '@src/views/ui-elements/cards/statistics/ProfitLineChart'
import CardTransactions from '@src/views/ui-elements/cards/advance/CardTransactions'
import CardBrowserStates from '@src/views/ui-elements/cards/advance/CardBrowserState'
import { isUserLoggedIn, getUserData } from '@utils'
import '@styles/react/libs/charts/apex-charts.scss'
import '@styles/base/pages/dashboard-ecommerce.scss'
import { AbilityContext } from '@src/utility/context/Can'

const EcommerceDashboard = () => {
  const { colors } = useContext(ThemeColors),
        ability = useContext(AbilityContext),
        trackBgColor = '#e9ecef'
  // ** State
  const [userData, setUserData] = useState(getUserData)
  console.log(userData.role)
  if (userData.role === 'ORGANIZATION') return (
    <div id='dashboard-ecommerce'>
        <Row className='match-height'>
          <Col xl='12' md='12' xs='12'>
          <CompanyTable />  
          </Col>
        </Row>
        <Row className='match-height'>
          <Col lg='4' md='12'>
            <Row className='match-height'>
              <Col lg='6' md='3' xs='6'>
                <OrdersBarChart warning={colors.warning.main} />
              </Col>
              <Col lg='6' md='3' xs='6'>
                <ProfitLineChart info={colors.info.main} />
              </Col>
              <Col lg='12' md='6' xs='12'>
                <Earnings success={colors.success.main} />
              </Col>
            </Row>
          </Col>
          <Col lg='8' md='12'>
            <RevenueReport primary={colors.primary.main} warning={colors.warning.main} />
          </Col>
        </Row>
    </div>)
}
/*
<CardMedal />
      <Row className='match-height'>
        <Col lg='8' xs='12'>
          
        </Col>
        <Col lg='4' md='6' xs='12'>
          <CardMeetup />
        </Col>
        <Col lg='4' md='6' xs='12'>
          <CardBrowserStates colors={colors} trackBgColor={trackBgColor} />
        </Col>
        <Col lg='4' md='6' xs='12'>
          <GoalOverview success={colors.success.main} />
        </Col>
        <Col lg='4' md='6' xs='12'>
          <CardTransactions />
        </Col>
      </Row>
*/
export default EcommerceDashboard