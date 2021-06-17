// ** Navigation sections imports
import apps from './apps'
import pages from './pages'
import others from './others'
import dashboards from './dashboards'
import uiElements from './ui-elements'
import formsAndTables from './forms-tables'
import chartsAndMaps from './charts-maps'

// ** Merge & Export
export default [...dashboards,  ...uiElements]//, ...formsAndTables, ...apps,...pages, ...chartsAndMaps, ...others
