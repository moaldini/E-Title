import { Box, Mail, MessageSquare, CheckSquare, Calendar, FileText, Circle, ShoppingCart, User, List, Plus } from 'react-feather'

export default [
  {
    id: 'apps',
    title: 'Users',
    icon: <User />,
    children: [
      {
        id: 'chat',
        title: 'Add User',
        icon: <Plus />,
        navLink: '/apps/chat'
      },
      {
        id: 'email',
        title: 'User List',
        icon: <List />,
        navLink: '/apps/email'
      }

    ]
  }
]
