<!--  -->
<template>
	<div class=''>
		<el-table :data="tableData" style="width: 100%">
			<el-table-column type="index" width="50">
			</el-table-column>
			<el-table-column prop="nickname" label="昵称" width="180">
			</el-table-column>
			<el-table-column prop="email" label="邮箱/账号" width="180">
			</el-table-column>
			<el-table-column prop="score" label="分值">
			</el-table-column>
			<el-table-column prop="uintroduce" label="分值">
			</el-table-column>
			<el-table-column prop="updateDate" label="创建时间">
			</el-table-column>
			<el-table-column label="操作">
			      <template slot-scope="scope">
			        
			        <el-button
			          size="mini"
			          type="primary"
					  @click="DeleteUser(scope.row.uid)"
			          >删除</el-button>
			      </template>
			    </el-table-column>
		</el-table>
		
		
	</div>
</template>

<script>
	import {
		userAll,removeUser
	} from '../../api/http.js'
	export default {
		components: {},
		data() {
			return {
				tableData: [],
				dialogVisible:false,
				id:'',
				scope:''
			};
		},
		//监听属性 类似于data概念
		computed: {},
		//监控data中的数据变化
		watch: {},
		//方法集合
		methods: {
			DeleteUser(id){
				removeUser({
					id:id,
				})
				.then(res=>{
					if(res.code == 200){
						this.$message.success('删除成功')
						this.dialogVisible =false
						this.scope = ''
						this.init()
					}
				}).catch(error=>{
					this.$message.error('删除失败')
				})
			},
			openUser(id){
				this.id = id
				this.dialogVisible = true
			},
			init() {
				userAll()
					.then(res => {
						if (res.code == 200) {
							this.tableData = res.result
							console.log(this.tableData)
						}
					}).catch(error => {
						this.$message.error('服务器错误')
					})
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
