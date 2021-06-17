import { Layers, Type, Droplet, Eye, CreditCard, Circle, Briefcase, Box, Layout, Database, Plus, List } from 'react-feather'

export default [
  {
    id: 'uiElements',
    title: 'Cars',
    icon: <Box />,
    children: [
      // {
      //   id: 'add_car',
      //   title: 'Add Car',
      //   icon: <Plus />,
      //   navLink: ''
      // },
      // {
      //   id: 'list_cars',
      //   title: 'List Cars',
      //   icon: <List />,
      //   navLink: ''
      // },
      {
        id: 'ticket',
        title: 'Title Ticket',
        icon: <List />,
        navLink: '/apps/ticket'
      }
      // ,
      // {
      //   id: 'todo',
      //   title: 'Todo',
      //   icon: <List />,
      //   navLink: '/apps/todo'
      // }
    ]
  }
]