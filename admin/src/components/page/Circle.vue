<!--  -->
<template>
	<div class=''>
		<br>
		<el-button type="primary" size="mini" @click="dialogVisible = true">圈子添加</el-button>
		<br><br>
		<el-table :data="tableData" style="width: 60%">
			<el-table-column type="index" width="50">
			</el-table-column>
			<el-table-column prop="cname" label="圈子名称" width="180">
			</el-table-column>
			<el-table-column prop="cintroduce" label="圈子介绍" width="200">
			</el-table-column>

			<el-table-column prop="createDate" label="创建时间">
			</el-table-column>
			<el-table-column label="操作">
				<template slot-scope="scope">
					<el-button size="mini" @click="OpenUpdate(scope.row)">修改</el-button>
				</template>
			</el-table-column>
		</el-table>

		<el-dialog title="圈子添加" :visible.sync="dialogVisible" width="30%">
			<el-form ref="form" :model="form" label-width="80px">
				<el-form-item label="圈子名称">
					<el-input v-model="form.name"></el-input>
				</el-form-item>
				<el-form-item label="圈子介绍">
					<el-input type="textarea" v-model="form.introduce"></el-input>
				</el-form-item>
				<el-form-item label="圈子图片">
					<el-upload class="avatar-uploader" action="http://127.0.0.1:8098/open/web/file/insert" :show-file-list="false"
					 :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
						<img v-if="form.head" :src="form.head" class="avatar">
						<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="dialogVisible = false">取 消</el-button>
				<el-button type="primary" @click="Add()">确 定</el-button>
			</span>
		</el-dialog>
		
		<el-dialog title="圈子更新" :visible.sync="dialogVisible2" width="30%">
			<el-form ref="form" :model="updateForm" label-width="80px">
				<el-form-item label="圈子名称">
					<el-input v-model="updateForm.cName"></el-input>
				</el-form-item>
				<el-form-item label="圈子介绍">
					<el-input type="textarea" v-model="updateForm.cIntroduce"></el-input>
				</el-form-item>
				<el-form-item label="圈子图片">
					<el-upload class="avatar-uploader" action="http://127.0.0.1:8098/open/web/file/insert" :show-file-list="false"
					 :on-success="handleAvatarSuccess2" :before-upload="beforeAvatarUpload">
						<img v-if="updateForm.head" :src="updateForm.head" class="avatar">
						<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="dialogVisible2 = false">取 消</el-button>
				<el-button type="primary" @click="update()">确 定</el-button>
			</span>
		</el-dialog>


	</div>
</template>

<script>
	import {
		circleAll,
		addCircle,
		updateCircle
	} from '../../api/http.js'
	export default {
		components: {},
		data() {
			return {
				tableData: [],
				dialogVisible: false,
				dialogVisible2:false,
				form: {
					head: ''
				},
				updateForm:{
					head:''
				}
			};
		},
		//监听属性 类似于data概念
		computed: {},
		//监控data中的数据变化
		watch: {},
		//方法集合
		methods: {
			update(){
				updateCircle(this.updateForm)
				.then(res=>{
					if(res.code == 200 ){
						this.$message.success('更新成功')
						this.dialogVisible2 =false
						this.init()
					}
				}).catch(error=>{
					this.$message.error('服务器错误')
				})
			},
			OpenUpdate(row){
				console.log(row)
				this.updateForm={
					cId:row.cid,
					cIntroduce:row.cintroduce,
					cName:row.cname,
					head:row.head
				}
				this.dialogVisible2 = true
			},
			init() {
				circleAll()
					.then(res => {
						if (res.code == 200) {
							this.tableData = res.result
							console.log(this.tableData)
						}
					}).catch(error => {
						this.$message.error('服务器错误')
					})
			},
			handleAvatarSuccess(res, file) {
				console.log(res.result)
				this.form.head = res.result
			},
			handleAvatarSuccess2(res, file) {
				console.log(res.result)
				this.updateForm.head = res.result
			},
			beforeAvatarUpload(file) {
				const isJPG = file.type === 'image/jpeg';
				const isLt2M = file.size / 1024 / 1024 < 2;

				if (!isJPG) {
					this.$message.error('上传头像图片只能是 JPG 格式!');
				}
				if (!isLt2M) {
					this.$message.error('上传头像图片大小不能超过 2MB!');
				}
				return isJPG && isLt2M;
			},
			Add(){
				addCircle({
					name:this.form.name,
					introduce:this.form.introduce,
					head:this.form.head
				}).then(res=>{
					if(res.code == 200){
						this.$message.success('添加成功')
						this.dialogVisible =false
						this.form = {
							head:''
						}
						this.init()
					}
				}).catch(error=>{
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
