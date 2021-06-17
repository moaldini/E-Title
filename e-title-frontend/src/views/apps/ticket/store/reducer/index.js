// ** Initial State
const initialState = {
  tasks: [],
  selectedTask: {},
  params: {
    filter: '',
    q: '',
    sort: '',
    tag: ''
  }
}

const TodoReducer = (state = initialState, action) => {
  switch (action.type) {
    case 'GET_TASKS':
      const tasks = action.tasks.map(t => ({...t}))
      return { ...state, tasks, params: action.params }
    case 'UPDATE_TASK':
      const index = state.tasks.findIndex((task) => task.vin === action.task.vin)
      if (index >= 0) {
        state.tasks[index] = action.task
      }

      return { ...state}
    case 'ADD_TASK':
        return { ...state, tasks: [...state.tasks, action.task]}
    case 'REORDER_TASKS':
      return { ...state, tasks: action.tasks }
    case 'SELECT_TASK':
      return { ...state, selectedTask: {...action.task} }
    default:
      return state
  }
}
export default TodoReducer
