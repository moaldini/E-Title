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
    case 'GET_TASKS_TODO':
      return { ...state, tasks: action.tasks, params: action.params }
    case 'UPDATE_TASKS_TODO':
      return { ...state }
    case 'REORDER_TASKS_TODO':
      return { ...state, tasks: action.tasks }
    case 'SELECT_TASK_TODO':
      return { ...state, selectedTask: action.task }
    default:
      return state
  }
}
export default TodoReducer
