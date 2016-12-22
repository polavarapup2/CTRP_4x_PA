describe(
		"coppa.js",
		function() {

			beforeEach(function() {

			});

			describe(
					"Wait indicators",
					function() {
						it(
								"should display wait panel",
								function() {
									displayWaitPanel();
									expect(
											document
													.getElementById('progress_indicator_panel').style.display)
											.toEqual("");
								});
						it(
								"should hide wait panel",
								function() {
									hideWaitPanel();
									expect(
											document
													.getElementById('progress_indicator_panel').style.display)
											.toEqual("none");
								});
					});

			describe(
					"Phone numbers",
					function() {
						it(
								"should extract extension properly",
								function() {
									expect(
											extractPhoneNumberExt("555-555-5555ext784"))
											.toEqual("784");
									expect(
											extractPhoneNumberExt("555-555-5555extn184"))
											.toEqual("184");
									expect(
											extractPhoneNumberExt("555-5555555x184"))
											.toEqual("184");
									expect(
											extractPhoneNumberExt("5555555555X184"))
											.toEqual("184");
									expect(
											extractPhoneNumberExt("555-555-5555"))
											.toEqual("");
								});
						it(
								"should remove extension from a phone number properly",
								function() {
									expect(
											extractPhoneNumberNoExt("555-555-5555ext784"))
											.toEqual("555-555-5555");
									expect(
											extractPhoneNumberNoExt("555-555-5555extn184"))
											.toEqual("555-555-5555");
									expect(
											extractPhoneNumberNoExt("555-5555555x184"))
											.toEqual("555-5555555");
									expect(
											extractPhoneNumberNoExt("5555555555X184"))
											.toEqual("5555555555");
									expect(
											extractPhoneNumberNoExt("555-555-5555"))
											.toEqual("555-555-5555");
								});

					});
		});
