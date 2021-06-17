// ** React Imports
import { useState, Fragment } from 'react'

// ** Third Party Components
import classnames from 'classnames'
import Flatpickr from 'react-flatpickr'
import { Editor } from 'react-draft-wysiwyg'
import { X, Star, Trash } from 'react-feather'
import Select, { components } from 'react-select'
import { EditorState, ContentState } from 'draft-js'
import { Modal, ModalBody, Button, Form, FormGroup, Input, Label, Media } from 'reactstrap'

// ** Utils
import { isObjEmpty, selectThemeColors } from '@utils'

// ** Assignee Avatars
import img1 from '@src/assets/images/portrait/small/avatar-s-3.jpg'
import img2 from '@src/assets/images/portrait/small/avatar-s-1.jpg'
import img3 from '@src/assets/images/portrait/small/avatar-s-4.jpg'
import img4 from '@src/assets/images/portrait/small/avatar-s-6.jpg'
import img5 from '@src/assets/images/portrait/small/avatar-s-2.jpg'
import img6 from '@src/assets/images/portrait/small/avatar-s-11.jpg'

// ** Styles Imports
import '@styles/react/libs/editor/editor.scss'
import '@styles/react/libs/react-select/_react-select.scss'
import '@styles/react/libs/flatpickr/flatpickr.scss'


// ** Function to capitalize the first letter of string
const capitalize = string => string.charAt(0).toUpperCase() + string.slice(1)

// ** Modal Header
const ModalHeader = props => {
  // ** Props
  const {
    children,
    store,
    handleTaskSidebar,
    setDeleted,
    deleted,
    important,
    setImportant,
    deleteTask,
    dispatch
  } = props

  // ** Function to delete task
  const handleDeleteTask = () => {
    setDeleted(!deleted)
    dispatch(deleteTask(store.selectedTask.vin))
    handleTaskSidebar()
  }

  return (
    <div className='modal-header d-flex align-items-center justify-content-between mb-1'>
      <h5 className='modal-title'>{children}</h5>
      <div className='todo-item-action d-flex align-items-center'>
        {store && !isObjEmpty(store.selectedTask) ? (
          <Trash className='cursor-pointer mt-25' size={16} onClick={() => handleDeleteTask()} />
        ) : null}
        <span className='todo-item-favorite cursor-pointer mx-75'>
          <Star
            size={16}
            onClick={() => setImportant(!important)}
            className={classnames({
              'text-warning': important === true
            })}
          />
        </span>
        <X className='font-weight-normal mt-25' size={16} onClick={handleTaskSidebar} />
      </div>
    </div>
  )
}

const TaskSidebar = props => {
  // ** Props
  const { open, handleTaskSidebar, store, dispatch, updateTask, selectTask, addTask, deleteTask } = props


  // ** Users
  const
    [vin, setVin] = useState(''),
    [make, setMake] = useState(''),
    [model, setModel] = useState(''),
    [color, setColor] = useState(''),
    [owner, setOwner] = useState(''),
    [miles, setMiles] = useState(''),
    [price, setPrice] = useState(''),
    [year, setYear] = useState(new Date()),
    [condition, setCondition] = useState(''),
    [cylinders, setCylinders] = useState(''),
    [transmission, setTransmission] = useState(''),
    [size, setSize] = useState(''),
    [fuel, setFuel] = useState(''),
    [title, setTitle] = useState(''),
    [vehicle, setVehicle] = useState(''),
    [type, setType] = useState(''),
    [state, setState] = useState(''),
    [completed, setCompleted] = useState(false),
    [important, setImportant] = useState(false),
    [deleted, setDeleted] = useState(false)


  // ** Assignee Select Options
  //  const assigneeOptions = [
  const makeOptions = [
    { value: 'toyota', label: 'Toyota', img: img1 },
    { value: 'ford', label: 'Ford', img: img2 },
    { value: 'honda', label: 'Honda', img: img3 },
    { value: 'chavlet', label: 'Chavlet', img: img4 },
    { value: 'bmw', label: 'BMW', img: img5 }
  ]

  // ** Tag Select Options
  const fuelOptions = [
    { value: 'GAS', label: 'GAS' },
    { value: 'ELECTRIC', label: 'ELECTRIC' },
    { value: 'DIESEL', label: 'DIESEL' },
    { value: 'HYBRID', label: 'HYBRID' }
    ]

  // ** Custom Assignee Component
  const AssigneeComponent = ({ data, ...props }) => {
    return (
      <components.Option {...props}>
        <Media className='align-items-center'>
          <img className='d-block rounded-circle mr-50' src={data.img} height='26' width='26' alt={data.label} />
          <Media body>
            <p className='mb-0'>{data.label}</p>
          </Media>
        </Media>
      </components.Option>
    )
  }

  // ** Returns sidebar title
  const handleSidebarTitle = () => {
    if (store && !isObjEmpty(store.selectedTask)) {
      return (
        <Button.Ripple
          outline
          size='sm'
          onClick={() => setCompleted(!completed)}
          color={completed === true ? 'success' : 'secondary'}
        >
          {completed === true ? 'Completed' : 'Mark Complete'}
        </Button.Ripple>
      )
    } else {
      return 'Add Task'
    }
  }

  // ** Function to run when sidebar opens
  const handleSidebarOpened = () => {
    const { selectedTask } = store
    if (!isObjEmpty(selectedTask)) {
      setVin(selectedTask.vin)
      setModel(selectedTask.model)
      setColor(selectedTask.color)
      setOwner(selectedTask.owner)
      setMiles(selectedTask.miles)
      setPrice(selectedTask.price)
      setCondition(selectedTask.condition)
      setCylinders(selectedTask.cylinders)
      setTransmission(selectedTask.transmission)
      setSize(selectedTask.size)
      setFuel(selectedTask.fuel)
      setTitle(selectedTask.title)
      setVehicle(selectedTask.vehicle)
      setType(selectedTask.type)
      setState(selectedTask.state)

      setCompleted(selectedTask.isCompleted)
      setImportant(selectedTask.isImportant)
      setMake(selectedTask.make)
      setYear(selectedTask.year)

    }
  }

  // ** Function to run when sidebar closes
  const handleSidebarClosed = () => {
    setVin('')
    setModel('')
    setColor('')
    setOwner('')
    setMiles('')
    setPrice('')
    setCondition('')
    setCylinders('')
    setTransmission('')
    setSize('')
    setFuel('')
    setTitle('')
    setVehicle('')
    setType('')
    setState('')
    setMake('')
    setCompleted(false)
    setImportant(false)
    setYear(new Date())
    dispatch(selectTask({}))
  }

  // ** Function to reset fileds
  const handleResetFields = () => {
    setVin(store.selectedTask.vin)
    setModel(store.selectedTask.model)
    setColor(store.selectedTask.color)
    setOwner(store.selectedTask.owner)
    setMiles(store.selectedTask.miles)
    setPrice(store.selectedTask.price)
    setCondition(store.selectedTask.condition)
    setCylinders(store.selectedTask.cylinders)
    setTransmission(store.selectedTask.transmission)
    setSize(store.selectedTask.size)
    setFuel(store.selectedTask.fuel)
    setTitle(store.selectedTask.title)
    setVehicle(store.selectedTask.vehicle)
    setType(store.selectedTask.type)
    setState(store.selectedTask.state)


    setCompleted(store.selectedTask.isCompleted)
    setImportant(store.selectedTask.isImportant)
    setDeleted(store.selectedTask.isDeleted)
    setYear(store.selectedTask.year)
    setMake(store.selectedTask.make)

    // if (store.selectedTask.tags.length) {
    //   const tags = []
    //   store.selectedTask.tags.map(tag => {
    //     tags.push({ value: tag, label: capitalize(tag) })
    //   })
    //   setTags(tags)
    // }
  }

  // ** Renders Footer Buttons
  const renderFooterButtons = () => {
    // const newTaskTag = []

    // const doesInclude = !isObjEmpty(store.selectedTask) && make.label === store.selectedTask.make.fullName

    // if (tags.length) {
    //   tags.map(tag => newTaskTag.push(tag.value))
    // }

    const formstate = {
      vin,
      make,
      model,
      color,
      owner,
      miles,
      price,
      condition,
      cylinders,
      transmission,
      size,
      fuel,
      title,
      vehicle,
      type,
      state,
      year,
      // year instanceof Date ? year.getUTCFullYear() : year
      isCompleted: completed,
      isDeleted: deleted,
      isImportant: important
    }

    if (store && !isObjEmpty(store.selectedTask)) {
      return (
        <Fragment>
          <Button
            color='primary'
            disabled={!vin.length || !color.length || !make.length || !model.length || !year.length || !cylinders.length || !transmission.length || !size.length || !fuel || !vehicle.length || !type.length || !owner.length }
           // disabled={!make.length }
            className='update-btn update-todo-item mr-1'
            onClick={() => {
              dispatch(updateTask({ ...formstate, id: store.selectedTask.vin }))
              handleTaskSidebar()
            }}
          >
            Update
          </Button>
          <Button color='secondary' onClick={handleResetFields} outline>
            Reset
          </Button>
        </Fragment>
      )
    } else {
      return (
        <Fragment>
          <Button
            color='primary'
            disabled={!vin.length || !color.length || !make.length || !model.length || !cylinders.length || !transmission.length || !size.length || !fuel || !vehicle.length || !type.length || !owner.length }
            className='add-todo-item mr-1'
            onClick={() => {
              dispatch(addTask(formstate))
              handleTaskSidebar()
            }}
          >
            Add
          </Button>
          <Button color='secondary' onClick={handleTaskSidebar} outline>
            Cancel
          </Button>
        </Fragment>
      )
    }
  }

  return (
    <Modal
      isOpen={open}
      toggle={handleTaskSidebar}
      className='sidebar-lg'
      contentClassName='p-0'
      onOpened={handleSidebarOpened}
      onClosed={handleSidebarClosed}
      modalClassName='modal-slide-in sidebar-todo-modal'
    >
      <Form id='form-modal-todo' className='todo-modal' onSubmit={e => e.preventDefault()}>
        <ModalHeader
          store={store}
          deleted={deleted}
          dispatch={dispatch}
          important={important}
          deleteTask={deleteTask}
          setDeleted={setDeleted}
          setImportant={setImportant}
          handleTaskSidebar={handleTaskSidebar}
        >
          {handleSidebarTitle()}
        </ModalHeader>
        <ModalBody className='flex-grow-1 pb-sm-0 pb-3'>
          <FormGroup>
            <Label className='form-label' for='task-title'>
              VIN <span className='text-danger'>*</span>
            </Label>
            <Input
              id='task-title'
              disabled = {store && !isObjEmpty(store.selectedTask)}
              value={vin}
              placeholder='VIN'
              className='new-todo-item-title'
              onChange={e => setVin(e.target.value)}
            />
          </FormGroup>

          <FormGroup>
            <Label className='form-label' for='task-make'>
              Make <span className='text-danger'>*</span>
            </Label>
            <Input
              id='task-make'
              disabled = {store && !isObjEmpty(store.selectedTask)}
              value={make}
              placeholder='Toyota'
              className='new-todo-item-title'
              onChange={e => setMake(e.target.value)}
            />
          </FormGroup>

          <FormGroup>
            <Label className='form-label' for='task-model'>
              Model <span className='text-danger'>*</span>
            </Label>
            <Input
              id='task-model'
              disabled = {store && !isObjEmpty(store.selectedTask)}
              value={model}
              placeholder='Camry'
              className='new-todo-item-title'
              onChange={e => setModel(e.target.value)}
            />
          </FormGroup>

          <FormGroup>
            <Label className='form-label' for='task-color'>
              Color <span className='text-danger'>*</span>
            </Label>
            <Input
              id='task-color'
              disabled = {store && !isObjEmpty(store.selectedTask)}
              value={color}
              placeholder='red'
              className='new-todo-item-title'
              onChange={e => setColor(e.target.value)}
            />
          </FormGroup>

          <FormGroup>
            <Label className='form-label' for='year'>
              Year
            </Label>
            <Flatpickr
              id='year'
              disabled = {store && !isObjEmpty(store.selectedTask)}
              name='2010'
              className='form-control'
              onChange={date => setYear(date[0])}
              value={year}
              options={{ dateFormat: 'Y' }}
            />
          </FormGroup>

          <FormGroup>
            <Label className='form-label' for='task-cylinders'>
              Cylinders <span className='text-danger'>*</span>
            </Label>
            <Input
              id='task-cylinders'
              disabled = {store && !isObjEmpty(store.selectedTask)}
              value={cylinders}
              placeholder='6'
              className='new-todo-item-title'
              onChange={e => setCylinders(e.target.value)}
            />
          </FormGroup>

          <FormGroup>
            <Label className='form-label' for='task-transmission'>
              Transmission <span className='text-danger'>*</span>
            </Label>
            <Input
              id='task-transmission'
              disabled = {store && !isObjEmpty(store.selectedTask)}
              value={transmission}
              placeholder='automatic'
              className='new-todo-item-title'
              onChange={e => setTransmission(e.target.value)}
            />
          </FormGroup>
          <FormGroup>
            <Label className='form-label' for='task-size'>
              Size <span className='text-danger'>*</span>
            </Label>
            <Input
              id='task-size'
              disabled = {store && !isObjEmpty(store.selectedTask)}
              value={size}
              placeholder='4'
              className='new-todo-item-title'
              onChange={e => setSize(e.target.value)}
            />
          </FormGroup>
          {/* <FormGroup>
            <Label className='form-label' for='task-fuelType'>
              Fuel <span className='text-danger'>*</span>
            </Label>
            <Input
              id='task-fuelType'
              disabled = {store && !isObjEmpty(store.selectedTask)}
              value={fuel}
              placeholder='fuelType'
              className='new-todo-item-title'
              onChange={e => setFuel(e.target.value)}
            />
          </FormGroup> */}
          <FormGroup>
            <Label className='form-label' for='task-tags'>
              Fuel <span className='text-danger'>*</span>
            </Label>
            <Select
              isMulti
              disabled = {store && !isObjEmpty(store.selectedTask)}
              id='task-tags'
              className='react-select'
              classNamePrefix='select'
              isClearable={false}
              options={fuelOptions}
              theme={selectThemeColors}
              value = {fuel ? { label: fuel, value: fuel} : null}
              // value={{label: fuel, value: fuel}}
              onChange={data => {
                debugger
                const [a] = data
                if (a) setFuel(a.label)
                else setFuel(null)
              }}
            />
          </FormGroup>

          <FormGroup>
            <Label className='form-label' for='task-vehicle'>
              Vehicle Type<span className='text-danger'>*</span>
            </Label>
            <Input
              id='task-vehicle'
              disabled = {store && !isObjEmpty(store.selectedTask)}
              value={vehicle}
              placeholder='4wd'
              className='new-todo-item-title'
              onChange={e => setVehicle(e.target.value)}
            />
          </FormGroup>
          <FormGroup>
            <Label className='form-label' for='task-type'>
              Body Type <span className='text-danger'>*</span>
            </Label>
            <Input
              id='task-type'
              disabled = {store && !isObjEmpty(store.selectedTask)}
              value={type}
              placeholder='SUV'
              className='new-todo-item-title'
              onChange={e => setType(e.target.value)}
            />
          </FormGroup>
          <FormGroup>
            <Label className='form-label' for='task-owner'>
              Owner ID<span className='text-danger'>*</span>
            </Label>
            <Input
              id='task-onwer'
              value={owner}
              placeholder='SSN or ID'
              className='new-todo-item-title'
              onChange={e => setOwner(e.target.value)}
            />
          </FormGroup>

          <FormGroup>
            <Label className='form-label' for='task-miles'>
              Miles
            </Label>
            <Input
              id='task-miles'
              value={miles}
              placeholder='100'
              className='new-todo-item-title'
              onChange={e => setMiles(e.target.value)}
            />
          </FormGroup>

          <FormGroup>
            <Label className='form-label' for='task-price'>
              Price
            </Label>
            <Input
              id='task-price'
              value={price}
              placeholder='1000'
              className='new-todo-item-title'
              onChange={e => setPrice(e.target.value)}
            />
          </FormGroup>

          <FormGroup>
            <Label className='form-label' for='task-condition'>
              Condition
            </Label>
            <Input
              id='task-condition'
              value={condition}
              placeholder='used'
              className='new-todo-item-title'
              onChange={e => setCondition(e.target.value)}
            />
          </FormGroup>

          <FormGroup>
            <Label className='form-label' for='task-title'>
              Title
            </Label>
            <Input
              id='task-title'
              value={title}
              placeholder='clean'
              className='new-todo-item-title'
              onChange={e => setTitle(e.target.value)}
            />
          </FormGroup>
  
          <FormGroup>
            <Label className='form-label' for='task-state'>
              State
            </Label>
            <Input
              id='task-state'
              value={state}
              placeholder='state'
              className='new-todo-item-title'
              onChange={e => setState(e.target.value)}
            />
          </FormGroup>

          <FormGroup className='my-1'>{renderFooterButtons()}</FormGroup>
        </ModalBody>
      </Form>
    </Modal>
  )
}

export default TaskSidebar
