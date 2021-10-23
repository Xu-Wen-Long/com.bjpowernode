<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link href="/crm/jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="/crm/jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="/crm/jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="/crm/jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/crm/jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="/crm/jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>


	<%--导入分页插件--%>
	<link href="/crm/jquery/bs_pagination/jquery.bs_pagination.min.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="/crm/jquery/bs_pagination/en.js"></script>
	<script type="text/javascript" src="/crm/jquery/bs_pagination/jquery.bs_pagination.min.js"></script>

</head>
<body>

	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">

					<form  id="saveForm" class="form-horizontal" role="form">

						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" name="owner" id="create-marketActivityOwner">

								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" name="name" id="create-marketActivityName">
                            </div>
						</div>

						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" name="startDate" id="create-startTime">
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" name="endDate" id="create-endTime">
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" name="cost" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" name="description" id="create-describe"></textarea>
							</div>
						</div>

					</form>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" onclick="saveActivity()" data-dismiss="modal">保存</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">

					<form class="form-horizontal" id="updateForm" role="form">

						<input type="hidden" name="id" id="id"></input>
						<div class="form-group">
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" name="owner" id="edit-marketActivityOwner">

								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" name="name" id="edit-marketActivityName">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" name="startDate" id="edit-startTime" >
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" name="endDate" id="edit-endTime" >
							</div>
						</div>

						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" name="cost" class="form-control" id="edit-cost" >
							</div>
						</div>

						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" name="description" rows="3" id="edit-describe"></textarea>
							</div>
						</div>

					</form>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="updateActivity()" data-dismiss="modal">更新</button>
				</div>
			</div>
		</div>
	</div>




	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">

			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="name">
				    </div>
				  </div>

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control time" type="text" id="startTime" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control time" type="text" id="endTime">
				    </div>
				  </div>

				  <button type="button" id="queryBtn" class="btn btn-default">查询</button>

				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" data-toggle="modal" onclick="openAddModal()" ><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" data-toggle="modal" onclick="openUpdateModal()"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" onclick="deleteBatch()"><span class="glyphicon glyphicon-minus"></span>删除</button>
					<button type="button" class="btn btn-primary" onclick="exportExcel()"><span class="glyphicon glyphicon-minus"></span>导出报表</button>
				</div>

			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="father"/></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>


					<tbody id="activityBoby">

					</tbody>
				</table>
			</div>

            <div style="height: 50px; position: relative;top: 30px;">
                <div id="activityPage"></div>
            </div>

		</div>

	</div>
	<script src="/crm/jquery/layer/layer.js"></script>
	<script>

		var rsc_bs_pag = {
			go_to_page_title: 'Go to page',
			rows_per_page_title: 'Rows per page',
			current_page_label: 'Page',
			current_page_abbr_label: 'p.',
			total_pages_label: 'of',
			total_pages_abbr_label: '/',
			total_rows_label: 'of',
			rows_info_records: 'records',
			go_top_text: '首页',
			go_prev_text: '上一页',
			go_next_text: '下一页',
			go_last_text: '末页'
		};

		refresh(1,4);
		function refresh(page,pageSize) {
                    $.get("/crm/workbench/activity/list",{
                        'page' : page,
                        'pageSize' : pageSize,
						//查询条件的数据
						'name' : $('#name').val(),
						'owner' : $('#owner').val(),
						'startTime' : $('#startTime').val(),
						'endDate' : $('#endTime').val()
                    }, function (data) {
                        var pageInfo = data;
                        $('#activityBoby').html("");
                        for (var i = 0; i < pageInfo.list.length; i++) {
                            var activity = pageInfo.list[i];
                            $('#activityBoby').append("<tr class=\"active\">\n" +
                                "\t\t\t\t\t\t\t<td><input type=\"checkbox\" value="+activity.id+" class='son' onclick='change()'/></td>\n" +
                                "\t\t\t\t\t\t\t<td><a style=\"text-decoration: none; cursor: pointer;\"  onclick=\"window.location.href='/crm/toView/workbench/activity/detail?id="+activity.id+"';\">" + activity.name + "</a></td>\n" +
                                "                            <td>" + activity.owner + "</td>\n" +
                                "\t\t\t\t\t\t\t<td>" + activity.startDate + "</td>\n" +
                                "\t\t\t\t\t\t\t<td>" + activity.endDate + "</td>\n" +
                                "\t\t\t\t\t\t</tr>");
                        }

                $("#activityPage").bs_pagination({
                    currentPage: pageInfo.pageNum, // 页码
                    rowsPerPage: pageInfo.pageSize, // 每页显示的记录条数
                    maxRowsPerPage: 11, // 每页最多显示的记录条数
                    totalPages: pageInfo.pages, // 总页数
                    totalRows: pageInfo.total, // 总记录条数
                    visiblePageLinks: 4, // 显示几个卡片
                    showGoToPage: true,
                    showRowsPerPage: true,
                    showRowsInfo: true,
                    showRowsDefaultInfo: true,
                    onChangePage: function (event , obj) {
                        refresh(obj.currentPage, obj.rowsPerPage);
                    }
                });
            }, 'json');
        }
		;(function($){
			$.fn.datetimepicker.dates['zh-CN'] = {
				days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"],
				daysShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日"],
				daysMin:  ["日", "一", "二", "三", "四", "五", "六", "日"],
				months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
				monthsShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
				today: "今天",
				suffix: [],
				meridiem: ["上午", "下午"]
			};
		}(jQuery));
        /*日历插件*/
        $(".time").datetimepicker({
            language:  "zh-CN",
            format: "yyyy-mm-dd",//显示格式
            minView: "month",//设置只显示到月份
            initialDate: new Date(),//初始化当前日期
            autoclose: true,//选中自动关闭
            todayBtn: true, //显示今日按钮
            clearBtn : true,
            pickerPosition: "bottom-left"
        });


        $("#queryBtn").bind('click',function (data) {
				refresh(1,3);
		})


		function openAddModal() {
			$('#createActivityModal').modal('show');
			$.get("/crm/workbench/activity/queryUsers",
				function (data) {
				//create-marketActivityOwner
				var context = "";
				for (var i = 0 ; i < data.length ; i++ ){
					var j = data[i];
					context += "<option value="+j.id+">"+j.name+"</option>"
				}
				$("#create-marketActivityOwner").html(context);
			},'json')
		}

		function saveActivity() {
			var form = $('#saveForm').serialize();
			$.post("/crm/workbench/activity/saveOrUpdate",form,
					function (data) {
				if (data.ok){
					alert(data.message)
					refresh(1,2);
				}
			},'json')

		}
		//父选框被选中，子选框进行操作
		$("#father").click(function () {
			/*$(".son").prop('checked' , $(this).prop('checked'))*/
			$('.son').prop('checked',$(this).prop('checked'));
		});

        function change() {
			var length = $('.son:checked').length;
			var length1 = $('.son').length;
			if (length == length1){
				$('#father').prop('checked',true);
			}else {
				$('#father').prop('checked',false);
			}

		}
		function openUpdateModal() {
        	var length = $('.son:checked').length;
        	if (length == 0){
				layer.alert("至少选中一个",{icon: 5});
			} else  if (length > 1){
        		layer.alert("只能单选进行修改",{icon: 5})
			} else {
        		//hid show
        		$('#editActivityModal').modal('show');

				var  id = $($('.son:checked')[0]).val();

				$.get("/crm/workbench/activity/queryById",{
					'id':id
				},function (data) {
					var activity = data;

					$('#edit-marketActivityName').val(activity.name);
					$('#edit-startTime').val(activity.startDate);
					$('#edit-endTime').val(activity.endDate);
					$('#edit-cost').val(activity.cost);
					$('#edit-describe').val(activity.description);
					//设置主键到隐藏域中
					$('#id').val(activity.id);

				$.get("/crm/workbench/activity/queryUsers",function (data) {
					var content = '';
					for (var i = 0 ; i < data.length ; i++ ){
						var user = data[i];
						content += "<option value="+user.id+">"+user.name+"</option>"
					}

					$("#edit-marketActivityOwner").html(content);
					$("#edit-marketActivityOwner").val(activity.owner);

					},'json');
				},'json')

			}

		}

		function deleteBatch() {
			var length = $('.son:checked').length;

			if (length == 0){
				layer.alert("至少选中一条记录", {icon: 5});
			}else{
				layer.alert("确定删除勾中的"+length+"条数据吗？", {
					time: 0 //不自动关闭
					,btn: ['确定', '取消']
					,yes: function(index){
						//点击确定按钮后，关闭弹窗
						layer.close(index);
						//异步删除
						//获取勾中的son的主键
						var ids = [];
						$('.son:checked').each(function () {
							var id = $(this).val();
							ids.push(id);
						});

						$.post("/crm/workbench/activity/deleteBatch",{'ids':ids.join()},
								function (data) {
									if(data.ok){
										layer.alert(data.message, {icon: 6});
										//刷新数据
										refresh(1,2);
									}

								},'json')
					}
				});
			}
		}
		function updateActivity() {
			$.post("/crm/workbench/activity/saveOrUpdate",$('#updateForm').serialize(),
					function (data) {
						if (data.ok){
							layer.alert(data.message, {icon: 6});
							refresh(1,2);
						}

			},'json')

		}
		function exportExcel() {
		location.href = "/crm//workbench/activity/exportExcel";

		}


	</script>
</body>
</html>