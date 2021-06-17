import { Card, CardHeader, CardTitle, CardBody, CardText } from 'reactstrap'

const BlockquoteStyling = () => {
  return (
    <Card>
      <CardHeader>
        <CardTitle tag='h4'>Blockquotes styling</CardTitle>
      </CardHeader>

      <CardBody>
        <CardText></CardText>
        <blockquote className='blockquote pl-1 border-left-primary border-left-3'>
          <CardText className='mb-0'>
            Sometimes when you innovate, you make mistakes. It is best to admit them quickly, and get on with improving
            your other innovations.
          </CardText>
          <footer className='blockquote-footer'>
            Steve Jobs <cite title='Source Title'>Entrepreneur</cite>
          </footer>
        </blockquote>
        <blockquote className='blockquote pr-1 text-right border-right-primary border-right-3'>
          <CardText className='mb-0'>
            Sometimes when you innovate, you make mistakes. It is best to admit them quickly, and get on with improving
            your other innovations.
          </CardText>
          <footer className='blockquote-footer'>
            Steve Jobs <cite title='Source Title'>Entrepreneur</cite>
          </footer>
        </blockquote>
      </CardBody>
    </Card>
  )
}
export default BlockquoteStyling
