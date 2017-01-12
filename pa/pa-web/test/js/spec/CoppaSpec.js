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

			describe("Date validations", function() {
				it("should detect valid dates", function() {
					expect(isValidDate("01/01/1990")).toBe(true);
					expect(isValidDate("01/31/2015")).toBe(true);
					expect(isValidDate("02/29/2016")).toBe(true);
				});
				it("should detect invalid dates", function() {
					expect(isValidDate("01/32/2015")).not.toBe(true);
					expect(isValidDate("02/30/2015")).not.toBe(true);
					expect(isValidDate("02/29/2015")).not.toBe(true);
					expect(isValidDate("28/02/2015")).not.toBe(true);
				});
			});
			
			describe("US phone validations", function() {
				it("should detect valid number", function() {
					expect(validateUSPhoneNumber("703-111-1111")).toBe(true);					
				});
				it("should detect invalid numbers", function() {
					expect(validateUSPhoneNumber("703-111-11111")).not.toBe(true);
					expect(validateUSPhoneNumber("(703)-111-1111")).not.toBe(true);
					expect(validateUSPhoneNumber("7031111111")).not.toBe(true);
				});
			});
			
			describe("email validations", function() {
				it("should detect valid email", function() {
					expect(validateEmailWithRegex("test@test.com")).toBe(true);
					expect(validateEmailWithRegex("test123-456@test.com")).toBe(true);
					expect(validateEmailWithRegex("test.test@test.co.in")).toBe(true);
				});
				it("should detect invalid email", function() {
					expect(validateEmailWithRegex("test.test!@test.com")).not.toBe(true);
					expect(validateEmailWithRegex("test.test%@test.com")).not.toBe(true);					
					expect(validateEmailWithRegex("test.test!@test..com")).not.toBe(true);					
				});
			});
			
			
			describe("extension validations", function() {
				it("should detect valid extensions", function() {
					expect(validateExtWithRegex("123")).toBe(true);
					expect(validateExtWithRegex("0123456789")).toBe(true);					
				});
				it("should detect invalid extensions", function() {
					expect(validateExtWithRegex("0123456789!")).not.toBe(true);
					expect(validateExtWithRegex("abc")).not.toBe(true);
					expect(validateExtWithRegex("123a'bc")).not.toBe(true);
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
