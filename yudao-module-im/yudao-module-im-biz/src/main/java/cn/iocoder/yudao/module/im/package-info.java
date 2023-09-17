/**
 * im 包下，即使通讯（Instant Messaging），实现管理后台的聊天功能
 *
 * 1. Controller URL：以 /im/ 开头，避免和其它 Module 冲突
 * 2. DataObject 表名：以 im_ 开头，方便在数据库中区分
 *
 * 注意，由于 IM 模块下，容易和其它模块重名，所以类名都加载 Im 的前缀~
 */
package cn.iocoder.yudao.module.im;
