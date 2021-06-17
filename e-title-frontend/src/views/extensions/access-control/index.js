import { useContext, useState, useEffect } from 'react'
import { AbilityContext } from '@src/utility/context/Can'
import { ChevronDown } from 'react-feather'
import DataTable from 'react-data-table-component'
import { Card, CardHeader, CardTitle } from 'reactstrap'
import { useParams } from 'react-router-dom'
import { useDispatch, useSelector } from 'react-redux'
import { getTasks, updateTask, selectTask, addTask, deleteTask, reOrderTasks } from '../../apps/ticket/store/actions'

import '@styles/react/libs/charts/apex-charts.scss'
import '@styles/base/pages/dashboard-ecommerce.scss'

const AccessControl = () => {
  const ability = useContext(AbilityContext)
  const [sort, setSort] = useState('')
  const [query, setQuery] = useState('')
  const store = useSelector(state => state.ticket)
  const dispatch = useDispatch()
  const paramsURL = useParams()
  const params = {
    filter: paramsURL.filter || '',
    q: query || '',
    sortBy: sort || '',
    tag: paramsURL.tag || ''
  }

  useEffect(() => {
    dispatch(
      getTasks({
        filter: paramsURL.filter || '',
        q: query || '',
        sortBy: sort || '',
        tag: paramsURL.tag || ''
      })
    )
  }, [store.tasks.length, paramsURL.filter, paramsURL.tag, query, sort])


/*0: {: "VỊN-222", : "Mitsubisihi", : "Pajero", : "black", : "1111", : "100000",…}
*/

  const basicColumns = [
    {
      name: 'VIN',
      selector: 'vin',
      sortable: true,
      maxWidth: '100px'
    },
    {
      name: 'Name',
      selector: 'make',
      sortable: true,
      minWidth: '225px'
    },
    {
      name: 'Model',
      selector: 'model',
      sortable: true,
      minWidth: '310px'
    },
    {
      name: 'Color',
      selector: 'color',
      sortable: true,
      minWidth: '250px'
    },
    {
      name: 'Owner',
      selector: 'owner',
      sortable: true,
      minWidth: '100px'
    },
    {
      name: 'Miles',
      selector: 'miles',
      sortable: true,
      minWidth: '175px'
    }
  ]
  return (
    <Card>
      <CardHeader>
        <CardTitle tag='h4'>My Cars</CardTitle>
      </CardHeader>
      <DataTable
        noHeader
        pagination
        data={store.tasks}
        columns={basicColumns}
        className='react-dataTable'
        sortIcon={<ChevronDown size={10} />}
        paginationRowsPerPageOptions={[10, 25, 50, 100]}
      />
    </Card>
  )
}
/*
    <Row>
      <Col className="hidden" md='6' sm='12'>
        <Card>
          <CardBody>
            <CardTitle tag='h4'>Common</CardTitle>
            <CardText>No ability is required to view this card</CardText>
            <CardText className='text-primary'>This card is visible to 'user' and 'admin' both</CardText>
          </CardBody>
        </Card>
      </Col>
      {ability.can('read', 'Analytics') ? (
        <Col md='6' sm='12'>
          <Card>
            <CardBody>
              <CardTitle tag='h4'>Analytics</CardTitle>
              <CardText>User with 'Analytics' subject's 'Read' ability can view this card</CardText>
              <CardText className='text-danger'>This card is visible to 'admin' only</CardText>
            </CardBody>
          </Card>
        </Col>
      ) : null}
    </Row>
    */
export default AccessControl
