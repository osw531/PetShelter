<%@ page contentType="text/html; charset=UTF-8"%>
<meta charset="utf-8"/>
<!DOCTYPE html>
<html lang="zxx" class="no-js">
<head>
<!-- Mobile Specific Meta -->
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Favicon-->
<link rel="shortcut icon" href="img/fav.png">
<!-- Author Meta -->
<meta name="author" content="codepixer">
<!-- Meta Description -->
<meta name="description" content="">
<!-- Meta Keyword -->
<meta name="keywords" content="">
<!-- meta character set -->
<meta charset="UTF-8">
<!-- Site Title -->
<title>Animal Shelter</title>

<link
	href="https://fonts.googleapis.com/css?family=Poppins:100,200,400,300,500,600,700"
	rel="stylesheet">
<!--
			CSS
			============================================= -->
<link rel="stylesheet" href="css/linearicons.css">
<link rel="stylesheet" href="css/font-awesome.min.css">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/magnific-popup.css">
<link rel="stylesheet" href="css/nice-select.css">
<link rel="stylesheet" href="css/animate.min.css">
<link rel="stylesheet" href="css/owl.carousel.css">
<link rel="stylesheet" href="css/main.css">
<style>
#googleMap{
	text-align:center;
	margin:auto;
}
</style>
<script>
		function myMap() {
			var latLng = new google.maps.LatLng(37.571066, 126.992255);
			var mapProp = {
				center : latLng,
				zoom : 18,
			};
			var map = new google.maps.Map(document.getElementById("googleMap"),
					mapProp);
			var marker = new google.maps.Marker({
				position: latLng,
				animation:google.maps.Animation.BOUNCE,
				icon:"/user/img/dogmarker.png"
				});

			marker.setMap(map); //맵에 마커 등록!!!
			var infowindow = new google.maps.InfoWindow(
					{
						content : "이곳이 <h3 style='color:red'><img src='https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTaC0GwlHHA2DidMj50Vec2SR4IUaj_nUW1lgBTppttSiSv386dxQ'></h3>이다!"
					});

			// Zoom to 9 when clicking on marker
			google.maps.event.addListener(marker, 'click', function() {
				map.setCenter(marker.getPosition());
				infowindow.open(map, marker);
				var pos = map.getZoom(); //기존에 설정된 줌의 중심점!!
				map.setZoom(15);
				//일정 시간뒤에 무언가 하고 싶을 때
				window.setTimeout(function() {
					map.setZoom(pos);
				}, 2400);
			});

		}
	</script>
</head>
<body>
 <%@include file="/user/inc/header.jsp" %>
	<!-- start banner Area -->
	<section class="banner-area relative" id="home">
		<div class="overlay overlay-bg"></div>
		<div class="container">
			<div class="row d-flex align-items-center justify-content-center">
				<div class="about-content col-lg-12">
					<h1 class="text-white">About Us</h1>
				</div>
			</div>
		</div>
	</section>
	<!-- End banner Area -->
	<br>
	<!-- Start home-about Area -->
	<section class="home-about-area">
		<div class="container-fluid">
			<div class="row align-items-center">
				<div class="col-lg-6 home-about-left no-padding">
					<img src="img/about-img.jpg" alt="" style="width: 70%">
				</div>
				<div class="col-lg-6 home-about-right no-padding">
					<h1>Our Mission</h1>
					<h5>We are here to listen from you deliver exellence</h5>
					<p>Abandoned Pet Shelter (APS), founded in 2019,IRS tax-exempt,
						qualified non-profit charitable organization that rescues and
						shelters abandoned, abused, and neglected pets, rehabilitates them
						and finds them new homes. APS is one of the largest no kill animal
						shelters in Republic of Korea and is devoted to the welfare of all
						animals.</p>
				</div>
			</div>
		</div>
	</section>
	<!-- End home-about Area -->

	<!-- Start testomial Area -->
	<section class="testomial-area section-gap" id="testimonail">
		<div class="container">
			<div class="row d-flex justify-content-center">
				<div class="menu-content pb-60 col-lg-8">
					<div class="title text-center">
						<h1 class="mb-10">Introducing our guardians who love dogs</h1>
						<p>Let me introduce you to the center manager who loves dogs.</p>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="active-testimonial-carusel">
					<div class="single-testimonial item">
						<img class="mx-auto" src="img/min.png" alt="">
						<p class="desc">He loves dogs and is always interested in
							problems with abandoned dogs, and he is a responsible manager. As
							a manager, He is doing his best for the puppies at the center.
						</h4>
						<h4>Kim Min Ho</h4>
						<p>CEO at Center</p>
					</div>
					<div class="single-testimonial item">
						<img class="mx-auto" src="img/kim.png" alt="">
						<p class="desc">She loves dogs and is always interested in
							problems with abandoned dogs, and she is a responsible manager.
							As a manager, She is doing her best for the puppies at the center.
						
						</h4>
						<h4>Kim Hyeon Hwa</h4>
						<p>Manager of Center</p>
					</div>
					<div class="single-testimonial item">
						<img class="mx-auto" src="img/kwon.png" alt="">
						<p class="desc">She loves dogs and is always interested in
							problems with abandoned dogs, and she is a responsible manager.
							As a manager, She is doing her best for the puppies at the center.
						
						</h4>
						<h4>Kwon Ji Young</h4>
						<p>Manager of Center</p>
					</div>
					<div class="single-testimonial item">
						<img class="mx-auto" src="img/dong.png" alt="">
						<p class="desc">He loves dogs and is always interested in
							problems with abandoned dogs, and he is a responsible manager. As
							a manager, He is doing his best for the puppies at the center.
						</h4>
						<h4>Hwang Dong Hyeon</h4>
						<p>Manager of Center</p>
					</div>
					<div class="single-testimonial item">
						<img class="mx-auto" src="img/sewon.png" alt="">
						<p class="desc">He loves dogs and is always interested in
							problems with abandoned dogs, and he is a responsible manager. As
							a manager, He is doing his best for the puppies at the center.
						</h4>
						<h4>Oh Se Won</h4>
						<p>Manager of Center</p>
					</div>
					<div class="single-testimonial item">
						<img class="mx-auto" src="img/zino1187.png" alt="">
						<p class="desc">The God of Coding</p>
						<h4>Min Zino</h4>
						<p>CEO at minssam.com</p>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- End testomial Area -->
	<!-- 구글 지도 -->
	<div id="googleMap" style="width: 70%; height: 500px;"></div>
	<br>
	<br>

	<!-- start footer Area -->
	<%@include file="/user/inc/footer.jsp" %>
	<!-- End footer Area -->

	<script src="js/vendor/jquery-2.2.4.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
		integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
		crossorigin="anonymous"></script>
	<script src="js/vendor/bootstrap.min.js"></script>
	<script	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC7s3c6u5G3n7koVQkGfBn_qLQarZjjHlc&callback=myMap" />
<!-- <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBhOdIF3Y9382fqJYt5I_sswSrEw5eihAA"></script> -->
	<script src="js/easing.min.js"></script>
	<script src="js/hoverIntent.js"></script>
	<script src="js/superfish.min.js"></script>
	<script src="js/jquery.ajaxchimp.min.js"></script>
	<script src="js/jquery.magnific-popup.min.js"></script>
	<script src="js/owl.carousel.min.js"></script>
	<script src="js/jquery.nice-select.min.js"></script>
	<script src="js/mail-script.js"></script>
	<script src="js/main.js"></script>
</body>
</html>