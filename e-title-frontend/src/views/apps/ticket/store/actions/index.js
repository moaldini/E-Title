import axios from 'axios'

import img1 from '@src/assets/images/portrait/small/avatar-s-3.jpg'
import img2 from '@src/assets/images/portrait/small/avatar-s-1.jpg'
import img3 from '@src/assets/images/portrait/small/avatar-s-4.jpg'
import img4 from '@src/assets/images/portrait/small/avatar-s-6.jpg'
import img5 from '@src/assets/images/portrait/small/avatar-s-2.jpg'
import img6 from '@src/assets/images/portrait/small/avatar-s-11.jpg'
// ** Assignee Avatars
// ** Get Tasks
// return axios.get('/apps/ticket/tasks', { params })
export const getTasks = params => {
  
return dispatch => {
        return axios.get('http://localhost:8888/cars', { params }).then(res => {
          
 //         const returnData = res.data[0]
  // const data = [{vin: returnData.vin, make: { fullName: returnData.make.name, avatar: img2 }, model: returnData.model.name, color: returnData.color, owner: returnData.owner, miles: returnData.miles, price: returnData.price, year: returnData.year, condition: returnData.condition, cylinders: returnData.cylinders, tranmission: returnData.transmission, size:  returnData.size, fuelType: returnData.fuelType, title: returnData.title, vehicle: returnData.vehicle, type: returnData.type, state: returnData.state, status: returnData.status}]
               dispatch({
        type: 'GET_TASKS',
        tasks: res.data,
        params
      })  
    })
  }
}

// ** Re-order Tasks on drag
export const reOrderTasks = tasks => dispatch => dispatch({ type: 'REORDER_TASKS', tasks })

// ** ADD Task
export const addTask = task => {
  return (dispatch, getState) => {
    task.year = typeof task.year === "string" ? task.year.substr(0, 4) : task.year instanceof Date ? task.year.getFullYear() : task.year
    // const data = {vin: task.vin, 'make': task.make.fullName, 'model': task.model, 'color': task.color, 'owner': task.owner, 'miles': task.miles, 'price': task.price, 'year': task.year, 'condition': task.condition, 'cylinders': task.cylinders, 'tranmission': task.transmission, 'size':  task.size, 'fuelType': task.fuelType, 'title': task.title, 'vehicle': task.vehicle, 'type': task.type, 'state': task.state}
    const data = {vin: task.vin, make: task.make, model: task.model, color: task.color, owner: task.owner, miles: task.miles, price: task.price, year: task.year, condition: task.condition, cylinders: task.cylinders, transmission: task.transmission, size:  task.size, fuel: task.fuel, title: task.title, vehicle: task.vehicle, type: task.type, state: task.state, status: 'PENDING'}
    debugger
      axios
      // .post('/apps/ticket/add-tasks', { task })
       .post('http://localhost:8888/cars', data)
      .then(res => {
        
        dispatch({
          type: 'ADD_TASK',
          task: res.data
        })
      })
      .then(dispatch(getTasks(getState().todo.params)))
  }
}

// ** Update Tasks
export const updateTask = task => {
 
  task.year = typeof task.year === "string" ? task.year.substr(0, 4) : task.year instanceof Date ? task.year.getFullYear() : task.year
  const data = {vin: task.vin, make: task.make, model: task.model, color: task.color, owner: task.owner, miles: task.miles, price: task.price, year: task.year, condition: task.condition, cylinders: task.cylinders, transmission: task.transmission, size:  task.size, fuel: task.fuel, title: task.title, vehicle: task.vehicle, type: task.type, state: task.state, status: 'PENDING'}
  const url = `http://localhost:8888/cars/${data.vin}`
  return (dispatch, getState) => {
    
    axios
      .put(url, data)
      .then(res => {
              
            dispatch({
          type: 'UPDATE_TASK',
          task: res.data
        })
      })
      .then(dispatch(getTasks(getState().todo.params)))
  }
}

// ** Delete Task
export const deleteTask = taskId => {
  return (dispatch, getState) => {
    axios
      .delete('/apps/ticket/delete-task', { taskId })
      .then(res => {
        dispatch({
          type: 'DELETE_TASK',
          task: res.data
        })
      })
      .then(() => dispatch(getTasks(getState().todo.params)))
  }
}

// ** Select Task
export const selectTask = task => dispatch => dispatch({ type: 'SELECT_TASK', task })
