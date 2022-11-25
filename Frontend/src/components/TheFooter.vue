<template>
<div>
	<footer class="container mt-5">
	<div class="row">
		<div class="col-sm-1">
			<b-img
            :src="require('@/assets/ssafy_logo.png')"
            id="logo"
            class="mx-auto d-block"
          ></b-img>
		</div>
		<div class="col-sm-9" style="margin-left:50px;" align="left">
			<h2>Developer Information</h2>
			<hr />
			<table class="table table-borderless">
				<tbody>
					<tr>
						<td>
							<b-img
							:src="require('@/assets/location-icon.png')"
							style="text-align:left; width: 20px; height: 20px"
						></b-img>
					 &nbsp; 광주광역시 광산구 하남산단6번로 107 (오선동)</td>
					</tr>
					<tr>
						<td>
							<b-img
							:src="require('@/assets/phone-symbol.png')"
							style="width: 20px; height: 20px"
						></b-img>
					 &nbsp; 강주형(kakao_panda@naver.com), 이광용(shinyyong@naver.com) </td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	</footer>
</div>
</template>