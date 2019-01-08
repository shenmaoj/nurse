﻿/*
 Copyright (c) 2003-2014, CKSource - Frederico Knabben. All rights reserved.
 For licensing, see LICENSE.md or http://ckeditor.com/license
*/
(function() {
	CKEDITOR.dialog.add("link", function(g) {
		var l = CKEDITOR.plugins.link,
			m = function() {
				var a = this.getDialog(),
					b = a.getContentElement("target", "popupFeatures"),
					a = a.getContentElement("target", "linkTargetName"),
					k = this.getValue();
				if(b && a) switch(b = b.getElement(), b.hide(), a.setValue(""), k) {
					case "frame":
						a.setLabel(g.lang.link.targetFrameName);
						a.getElement().show();
						break;
					case "popup":
						b.show();
						a.setLabel(g.lang.link.targetPopupName);
						a.getElement().show();
						break;
					default:
						a.setValue(k), a.getElement().hide()
				}
			},
			f = function(a) {
				a.target && this.setValue(a.target[this.id] || "")
			},
			h = function(a) {
				a.advanced && this.setValue(a.advanced[this.id] || "")
			},
			i = function(a) {
				a.target || (a.target = {});
				a.target[this.id] = this.getValue() || ""
			},
			j = function(a) {
				a.advanced || (a.advanced = {});
				a.advanced[this.id] = this.getValue() || ""
			},
			c = g.lang.common,
			b = g.lang.link,
			d;
		return {
			title: b.title,
			minWidth: 350,
			minHeight: 230,
			contents: [{
				id: "info",
				label: b.info,
				title: b.info,
				elements: [{
					id: "linkType",
					type: "select",
					label: b.type,
					"default": "url",
					items: [
						[b.toUrl, "url"],
						[b.toAnchor, "anchor"],
						[b.toEmail, "email"]
					],
					onChange: function() {
						var a = this.getDialog(),
							b = ["urlOptions", "anchorOptions", "emailOptions"],
							k = this.getValue(),
							e = a.definition.getContents("upload"),
							e = e && e.hidden;
						"url" == k ? (g.config.linkShowTargetTab && a.showPage("target"), e || a.showPage("upload")) : (a.hidePage("target"), e || a.hidePage("upload"));
						for(e = 0; e < b.length; e++) {
							var c = a.getContentElement("info", b[e]);
							c && (c = c.getElement().getParent().getParent(), b[e] == k + "Options" ? c.show() : c.hide())
						}
						a.layout()
					},
					setup: function(a) {
						this.setValue(a.type ||
							"url")
					},
					commit: function(a) {
						a.type = this.getValue()
					}
				}, {
					type: "vbox",
					id: "urlOptions",
					children: [{
						type: "hbox",
						widths: ["25%", "75%"],
						children: [{
							id: "protocol",
							type: "select",
							label: c.protocol,
							"default": "http://",
							items: [
								["http://‎", "http://"],
								["https://‎", "https://"],
								["ftp://‎", "ftp://"],
								["news://‎", "news://"],
								[b.other, ""]
							],
							setup: function(a) {
								a.url && this.setValue(a.url.protocol || "")
							},
							commit: function(a) {
								a.url || (a.url = {});
								a.url.protocol = this.getValue()
							}
						}, {
							type: "text",
							id: "url",
							label: c.url,
							required: !0,
							onLoad: function() {
								this.allowOnChange = !0
							},
							onKeyUp: function() {
								this.allowOnChange = !1;
								var a = this.getDialog().getContentElement("info", "protocol"),
									b = this.getValue(),
									k = /^((javascript:)|[#\/\.\?])/i,
									c = /^(http|https|ftp|news):\/\/(?=.)/i.exec(b);
								c ? (this.setValue(b.substr(c[0].length)), a.setValue(c[0].toLowerCase())) : k.test(b) && a.setValue("");
								this.allowOnChange = !0
							},
							onChange: function() {
								if(this.allowOnChange) this.onKeyUp()
							},
							validate: function() {
								var a = this.getDialog();
								return a.getContentElement("info", "linkType") && "url" != a.getValueOf("info", "linkType") ?
									!0 : !g.config.linkJavaScriptLinksAllowed && /javascript\:/.test(this.getValue()) ? (alert(c.invalidValue), !1) : this.getDialog().fakeObj ? !0 : CKEDITOR.dialog.validate.notEmpty(b.noUrl).apply(this)
							},
							setup: function(a) {
								this.allowOnChange = !1;
								a.url && this.setValue(a.url.url);
								this.allowOnChange = !0
							},
							commit: function(a) {
								this.onChange();
								a.url || (a.url = {});
								a.url.url = this.getValue();
								this.allowOnChange = !1
							}
						}],
						setup: function() {
							this.getDialog().getContentElement("info", "linkType") || this.getElement().show()
						}
					}, {
						type: "button",
						id: "browse",
						hidden: "true",
						filebrowser: "info:url",
						label: c.browseServer
					}]
				}, {
					type: "vbox",
					id: "anchorOptions",
					width: 260,
					align: "center",
					padding: 0,
					children: [{
						type: "fieldset",
						id: "selectAnchorText",
						label: b.selectAnchor,
						setup: function() {
							d = l.getEditorAnchors(g);
							this.getElement()[d && d.length ? "show" : "hide"]()
						},
						children: [{
							type: "hbox",
							id: "selectAnchor",
							children: [{
								type: "select",
								id: "anchorName",
								"default": "",
								label: b.anchorName,
								style: "width: 100%;",
								items: [
									[""]
								],
								setup: function(a) {
									this.clear();
									this.add("");
									if(d)
										for(var b =
												0; b < d.length; b++) d[b].name && this.add(d[b].name);
									a.anchor && this.setValue(a.anchor.name);
									(a = this.getDialog().getContentElement("info", "linkType")) && "email" == a.getValue() && this.focus()
								},
								commit: function(a) {
									a.anchor || (a.anchor = {});
									a.anchor.name = this.getValue()
								}
							}, {
								type: "select",
								id: "anchorId",
								"default": "",
								label: b.anchorId,
								style: "width: 100%;",
								items: [
									[""]
								],
								setup: function(a) {
									this.clear();
									this.add("");
									if(d)
										for(var b = 0; b < d.length; b++) d[b].id && this.add(d[b].id);
									a.anchor && this.setValue(a.anchor.id)
								},
								commit: function(a) {
									a.anchor ||
										(a.anchor = {});
									a.anchor.id = this.getValue()
								}
							}],
							setup: function() {
								this.getElement()[d && d.length ? "show" : "hide"]()
							}
						}]
					}, {
						type: "html",
						id: "noAnchors",
						style: "text-align: center;",
						html: '<div role="note" tabIndex="-1">' + CKEDITOR.tools.htmlEncode(b.noAnchors) + "</div>",
						focus: !0,
						setup: function() {
							this.getElement()[d && d.length ? "hide" : "show"]()
						}
					}],
					setup: function() {
						this.getDialog().getContentElement("info", "linkType") || this.getElement().hide()
					}
				}, {
					type: "vbox",
					id: "emailOptions",
					padding: 1,
					children: [{
						type: "text",
						id: "emailAddress",
						label: b.emailAddress,
						required: !0,
						validate: function() {
							var a = this.getDialog();
							return !a.getContentElement("info", "linkType") || "email" != a.getValueOf("info", "linkType") ? !0 : CKEDITOR.dialog.validate.notEmpty(b.noEmail).apply(this)
						},
						setup: function(a) {
							a.email && this.setValue(a.email.address);
							(a = this.getDialog().getContentElement("info", "linkType")) && "email" == a.getValue() && this.select()
						},
						commit: function(a) {
							a.email || (a.email = {});
							a.email.address = this.getValue()
						}
					}, {
						type: "text",
						id: "emailSubject",
						label: b.emailSubject,
						setup: function(a) {
							a.email && this.setValue(a.email.subject)
						},
						commit: function(a) {
							a.email || (a.email = {});
							a.email.subject = this.getValue()
						}
					}, {
						type: "textarea",
						id: "emailBody",
						label: b.emailBody,
						rows: 3,
						"default": "",
						setup: function(a) {
							a.email && this.setValue(a.email.body)
						},
						commit: function(a) {
							a.email || (a.email = {});
							a.email.body = this.getValue()
						}
					}],
					setup: function() {
						this.getDialog().getContentElement("info", "linkType") || this.getElement().hide()
					}
				}]
			}, {
				id: "target",
				requiredContent: "a[target]",
				label: b.target,
				title: b.target,
				elements: [{
					type: "hbox",
					widths: ["50%", "50%"],
					children: [{
						type: "select",
						id: "linkTargetType",
						label: c.target,
						"default": "notSet",
						style: "width : 100%;",
						items: [
							[c.notSet, "notSet"],
							[b.targetFrame, "frame"],
							[b.targetPopup, "popup"],
							[c.targetNew, "_blank"],
							[c.targetTop, "_top"],
							[c.targetSelf, "_self"],
							[c.targetParent, "_parent"]
						],
						onChange: m,
						setup: function(a) {
							a.target && this.setValue(a.target.type || "notSet");
							m.call(this)
						},
						commit: function(a) {
							a.target || (a.target = {});
							a.target.type = this.getValue()
						}
					}, {
						type: "text",
						id: "linkTargetName",
						label: b.targetFrameName,
						"default": "",
						setup: function(a) {
							a.target && this.setValue(a.target.name)
						},
						commit: function(a) {
							a.target || (a.target = {});
							a.target.name = this.getValue().replace(/\W/gi, "")
						}
					}]
				}, {
					type: "vbox",
					width: "100%",
					align: "center",
					padding: 2,
					id: "popupFeatures",
					children: [{
						type: "fieldset",
						label: b.popupFeatures,
						children: [{
							type: "hbox",
							children: [{
								type: "checkbox",
								id: "resizable",
								label: b.popupResizable,
								setup: f,
								commit: i
							}, {
								type: "checkbox",
								id: "status",
								label: b.popupStatusBar,
								setup: f,
								commit: i
							}]
						}, {
							type: "hbox",
							children: [{
								type: "checkbox",
								id: "location",
								label: b.popupLocationBar,
								setup: f,
								commit: i
							}, {
								type: "checkbox",
								id: "toolbar",
								label: b.popupToolbar,
								setup: f,
								commit: i
							}]
						}, {
							type: "hbox",
							children: [{
								type: "checkbox",
								id: "menubar",
								label: b.popupMenuBar,
								setup: f,
								commit: i
							}, {
								type: "checkbox",
								id: "fullscreen",
								label: b.popupFullScreen,
								setup: f,
								commit: i
							}]
						}, {
							type: "hbox",
							children: [{
								type: "checkbox",
								id: "scrollbars",
								label: b.popupScrollBars,
								setup: f,
								commit: i
							}, {
								type: "checkbox",
								id: "dependent",
								label: b.popupDependent,
								setup: f,
								commit: i
							}]
						}, {
							type: "hbox",
							children: [{
								type: "text",
								widths: ["50%", "50%"],
								labelLayout: "horizontal",
								label: c.width,
								id: "width",
								setup: f,
								commit: i
							}, {
								type: "text",
								labelLayout: "horizontal",
								widths: ["50%", "50%"],
								label: b.popupLeft,
								id: "left",
								setup: f,
								commit: i
							}]
						}, {
							type: "hbox",
							children: [{
								type: "text",
								labelLayout: "horizontal",
								widths: ["50%", "50%"],
								label: c.height,
								id: "height",
								setup: f,
								commit: i
							}, {
								type: "text",
								labelLayout: "horizontal",
								label: b.popupTop,
								widths: ["50%", "50%"],
								id: "top",
								setup: f,
								commit: i
							}]
						}]
					}]
				}]
			}, {
				id: "upload",
				label: b.upload,
				title: b.upload,
				hidden: !0,
				filebrowser: "uploadButton",
				elements: [{
					type: "file",
					id: "upload",
					label: c.upload,
					style: "height:40px",
					size: 29
				}, {
					type: "fileButton",
					id: "uploadButton",
					label: c.uploadSubmit,
					filebrowser: "info:url",
					"for": ["upload", "upload"]
				}]
			}, {
				id: "advanced",
				label: b.advanced,
				title: b.advanced,
				elements: [{
					type: "vbox",
					padding: 1,
					children: [{
						type: "hbox",
						widths: ["45%", "35%", "20%"],
						children: [{
							type: "text",
							id: "advId",
							requiredContent: "a[id]",
							label: b.id,
							setup: h,
							commit: j
						}, {
							type: "select",
							id: "advLangDir",
							requiredContent: "a[dir]",
							label: b.langDir,
							"default": "",
							style: "width:110px",
							items: [
								[c.notSet, ""],
								[b.langDirLTR, "ltr"],
								[b.langDirRTL, "rtl"]
							],
							setup: h,
							commit: j
						}, {
							type: "text",
							id: "advAccessKey",
							requiredContent: "a[accesskey]",
							width: "80px",
							label: b.acccessKey,
							maxLength: 1,
							setup: h,
							commit: j
						}]
					}, {
						type: "hbox",
						widths: ["45%", "35%", "20%"],
						children: [{
							type: "text",
							label: b.name,
							id: "advName",
							requiredContent: "a[name]",
							setup: h,
							commit: j
						}, {
							type: "text",
							label: b.langCode,
							id: "advLangCode",
							requiredContent: "a[lang]",
							width: "110px",
							"default": "",
							setup: h,
							commit: j
						}, {
							type: "text",
							label: b.tabIndex,
							id: "advTabIndex",
							requiredContent: "a[tabindex]",
							width: "80px",
							maxLength: 5,
							setup: h,
							commit: j
						}]
					}]
				}, {
					type: "vbox",
					padding: 1,
					children: [{
						type: "hbox",
						widths: ["45%", "55%"],
						children: [{
							type: "text",
							label: b.advisoryTitle,
							requiredContent: "a[title]",
							"default": "",
							id: "advTitle",
							setup: h,
							commit: j
						}, {
							type: "text",
							label: b.advisoryContentType,
							requiredContent: "a[type]",
							"default": "",
							id: "advContentType",
							setup: h,
							commit: j
						}]
					}, {
						type: "hbox",
						widths: ["45%", "55%"],
						children: [{
							type: "text",
							label: b.cssClasses,
							requiredContent: "a(cke-xyz)",
							"default": "",
							id: "advCSSClasses",
							setup: h,
							commit: j
						}, {
							type: "text",
							label: b.charset,
							requiredContent: "a[charset]",
							"default": "",
							id: "advCharset",
							setup: h,
							commit: j
						}]
					}, {
						type: "hbox",
						widths: ["45%", "55%"],
						children: [{
							type: "text",
							label: b.rel,
							requiredContent: "a[rel]",
							"default": "",
							id: "advRel",
							setup: h,
							commit: j
						}, {
							type: "text",
							label: b.styles,
							requiredContent: "a{cke-xyz}",
							"default": "",
							id: "advStyles",
							validate: CKEDITOR.dialog.validate.inlineStyle(g.lang.common.invalidInlineStyle),
							setup: h,
							commit: j
						}]
					}]
				}]
			}],
			onShow: function() {
				var a =
					this.getParentEditor(),
					b = a.getSelection(),
					c = null;
				(c = l.getSelectedLink(a)) && c.hasAttribute("href") ? b.getSelectedElement() || b.selectElement(c) : c = null;
				a = l.parseLinkAttributes(a, c);
				this._.selectedElement = c;
				this.setupContent(a)
			},
			onOk: function() {
				var a = {};
				this.commitContent(a);
				var b = g.getSelection(),
					c = l.getLinkAttributes(g, a);
				var fileName;
				if(c.set["data-cke-saved-href"].indexOf("※") != -1){
					fileName = c.set["data-cke-saved-href"].split("※")[1];
					c.set["data-cke-saved-href"] = c.set["data-cke-saved-href"].split("※")[0];
					c.set["href"] = c.set["data-cke-saved-href"].split("※")[0];
				}else{
					fileName = c.set["data-cke-saved-href"];
				}
				if(this._.selectedElement) {
					var e = this._.selectedElement,
						d = e.data("cke-saved-href"),
						f = e.getHtml();
					e.setAttributes(c.set);
					e.removeAttributes(c.removed);
					if(d == f || "email" == a.type && -1 !=
						f.indexOf("@")) {
						e.setHtml("email" == a.type ? a.email.address : fileName), b.selectElement(e);
					}
					delete this._.selectedElement
				} else b = b.getRanges()[0], b.collapsed && (a = new CKEDITOR.dom.text("email" == a.type ? a.email.address : fileName, g.document), b.insertNode(a), b.selectNodeContents(a)), c = new CKEDITOR.style({
					element: "a",
					attributes: c.set
				}), c.type = CKEDITOR.STYLE_INLINE, c.applyToRange(b, g), b.select()
			},
			onLoad: function() {
				g.config.linkShowAdvancedTab || this.hidePage("advanced");
				g.config.linkShowTargetTab ||
					this.hidePage("target")
			},
			onFocus: function() {
				var a = this.getContentElement("info", "linkType");
				a && "url" == a.getValue() && (a = this.getContentElement("info", "url"), a.select())
			}
		}
	})
})();