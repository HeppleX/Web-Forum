<!--  -->
<template>
	<div class=''>
		<el-table :data="tableData" style="width: 60%">
			<el-table-column type="index" width="50">
			</el-table-column>
			<el-table-column prop="user.nickname" label="用户" width="180">
			</el-table-column>
			<el-table-column prop="content" label="留言内容" width="200">
			</el-table-column>
			
			<el-table-column prop="createDate" label="留言时间">
			</el-table-column>
			<el-table-column label="操作" width="200">
				<template slot-scope="scope">
					<el-button size="mini" type = 'danger' @click="DeleteMessage(scope.row.id)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>
	</div>
</template>

<script>
	import {
		messageAll,deleteMessage
	} from '../../api/http.js'
	export default {
		components: {},
		data() {
			return {
				tableData: []
			};
		},
		//监听属性 类似于data概念
		computed: {},
		//监控data中的数据变化
		watch: {},
		//方法集合
		methods: {
			init() {
				messageAll()
					.then(res => {
						if (res.code == 200) {
							this.tableData = res.result
							console.log(this.tableData)
						}
					}).catch(error => {
						this.$message.error('服务器错误')
					})
			},
			DeleteMessage(id){
				
				this.$confirm('此操作将永久删除该, 是否继续?', '提示', {
				          confirmButtonText: '确定',
				          cancelButtonText: '取消',
				          type: 'warning'
				        }).then(() => {
				          deleteMessage({
				          	id:id
				          }).then(res=>{
				          	if(res.code == 200){
				          		this.$message.success('操作成功!')
				          		this.init()
				          	}
				          }).catch(error=>{
				          	this.$message.error("服务器错误!")
				          })
				        }).catch(() => {
				          this.$message({
				            type: 'info',
				            message: '已取消删除'
				          });          
				        });
				
				
			}
		},
		//生命周期 - 创建完成（可以访问当前this实例）
		created() {
			this.init()
		},
		//生命周期 - 挂载完成（可以访问DOM元素）
		mounted() {

		},
		beforeCreate() {}, //生命周期 - 创建之前
		beforeMount() {}, //生命周期 - 挂载之前
		beforeUpdate() {}, //生命周期 - 更新之前
		updated() {}, //生命周期 - 更新之后
		beforeDestroy() {}, //生命周期 - 销毁之前
		destroyed() {}, //生命周期 - 销毁完成
		activated() {}, //如果页面有keep-alive缓存功能，这个函数会触发
	}
</script>
<style scoped>

</style>
