<!--  -->
<template>
	<div class=''>
		<el-table :data="tableData" style="width: 100%">
			<el-table-column type="index" width="50">
			</el-table-column>
			<el-table-column prop="title" label="标题" width="180">
			</el-table-column>
			<el-table-column prop="circle.cname" label="圈子" width="180">
			</el-table-column>
			<el-table-column prop="score" label="热度">
			</el-table-column>
			<el-table-column prop="user.nickname" label="作者">
			</el-table-column>
			<el-table-column prop="createDate" label="创建时间">
			</el-table-column>
			<el-table-column label="操作">
				<template slot-scope="scope">
					<el-button size="mini" @click="toArticle(scope.row.cid)">查看</el-button>
				</template>
			</el-table-column>
		</el-table>
	</div>
</template>

<script>
	import {
		articleAll
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
				articleAll()
					.then(res => {
						if (res.code == 200) {
							this.tableData = res.result
							console.log(this.tableData)
						}
					}).catch(error => {
						this.$message.error('服务器错误')
					})
			},
			toArticle(id){
				console.log(id)
				window.open('http://localhost:9099/#/show/article/'+id)
				
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
