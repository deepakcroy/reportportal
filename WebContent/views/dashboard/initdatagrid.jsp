<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="net.aditri.ui.dashboard.*"%>
<%@ page import="net.aditri.web.utility.KeyValue"%>
<style>
.well-black .widget-content-datagrid{ color:gray; background-color:#fff;}

.well-black .boo-table.table-bordered {
  border-color: #c8ccce
}

.well-black .boo-table.table-bordered thead th,
.well-black .boo-table.table-bordered thead td {
  border-left-color: #d5d5d5;
}

.well-black .boo-table.table-content tbody th,
.well-black .boo-table.table-content tbody td,
.well-black .boo-table.table-bordered tbody th,
.well-black .boo-table.table-bordered tbody td {
  border-left-color: #d5d5d5;
  border-left-style: solid;
}

.well-black .boo-table.table-content th:first-child,
.well-black .boo-table.table-content td:first-child {
  border-left: none;
}

.well-black .boo-table.table-bordered tr th:first-child,
.well-black .boo-table.table-bordered tr td:first-child {
  border-left: 1px solid #c8ccce;
}

.well-black .boo-table thead th,
.well-black .boo-table thead td,
.well-black .boo-table tfoot th,
.well-black .boo-table tfoot td {
  color: #2E3038;
  vertical-align: top;
  background: #e5e7ec;
  background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #ffffff), color-stop(1%, #f5f6fa), color-stop(85%, #e5e7ec));
  background: -webkit-linear-gradient(top, #ffffff 0%, #f5f6fa 1%, #e5e7ec 85%);
  background: -moz-linear-gradient(top, #ffffff 0%, #f5f6fa 1%, #e5e7ec 85%);
  background: -ms-linear-gradient(top, #ffffff 0%, #f5f6fa 1%, #e5e7ec 85%);
  background: -o-linear-gradient(top, #ffffff 0%, #f5f6fa 1%, #e5e7ec 85%);
  background: linear-gradient(to bottom, #ffffff 0%, #f5f6fa 1%, #e5e7ec 85%);
filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#fff5f6fa', endColorstr='#ffe5e7ec', GradientType=0 );
 filter: progid:dximagetransform.microsoft.gradient(enabled=false);
  text-shadow: 0 1px 0 rgba(255, 255, 255, 1);
}

.well-black .boo-table thead th,
.well-black .boo-table thead td {
  border-left-color: #b4b7be;
  border-bottom: 1px solid #b4b7be;
}

.well-black .boo-table tfoot th,
.well-black .boo-table tfoot td {
  border-left-color: #b4b7be;
  border-top: 1px solid #b4b7be;
}

.well-black .boo-table tfoot.total th,
.well-black .boo-table tfoot.total td,
.well-black .boo-table tfoot .total th,
.well-black .boo-table tfoot .total td {
  background: #ffdf7d;
  border-top-color: #d9b751;
  border-left-color: #d9b751;
  padding-top: 4px;
  padding-bottom: 4px;
}



.well-black .boo-table.table-bordered caption + thead th,
.well-black .boo-table.table-bordered caption + thead td {
  border-bottom-color: #d5d5d5;
}

.well-black .boo-table.table-light thead th,
.well-black .boo-table.table-light thead td {
  border-bottom-color: #d1d2d5;
}

.well-black .boo-table tbody td {
  border-color-top: #d5d5d5;
}

.well-black .boo-table.table-hover tbody tr:first-child td,
.well-black .boo-table.table-hover tbody tr:first-child:hover td {
  border-top: 1px solid white;
}
.well-black .widget-content-datagrid .table-striped tbody tr:nth-child(odd) td,
.well-black .widget-content-datagrid .table-striped tbody tr:nth-child(odd) th {
    background-color: #f3f3f3;
}
.well-black .widget-content-datagrid .table caption, 
.well-black .widget-content-datagrid .table thead th, 
.well-black .widget-content-datagrid .table tfoot th,
.well-black .widget-content-datagrid .table tbody {
    text-shadow: none;
}

.well-black .widget-content-datagrid .table th,
.well-black .widget-content-datagrid .table td,
.well-black .widget-content-datagrid .table-content th,
.well-black .widget-content-datagrid .table-content td {
    border-top-color: #a3abb1;
}

.well-black .widget-content-datagrid .table-condensed th, .table-condensed td {
    padding: 1px 5px;
}

</style>


<div class="widget" style="margin: 0 auto">
<%
if (request.getAttribute("tableHTML")!=null)
{
	out.println(request.getAttribute("tableHTML"));
}
 %>
</div>
