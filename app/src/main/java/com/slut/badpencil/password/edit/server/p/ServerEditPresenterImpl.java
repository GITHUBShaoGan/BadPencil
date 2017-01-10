package com.slut.badpencil.password.edit.server.p;

import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.bean.password.ServerPassword;
import com.slut.badpencil.password.edit.server.m.ServerEditModel;
import com.slut.badpencil.password.edit.server.m.ServerEditModelImpl;
import com.slut.badpencil.password.edit.server.v.ServerEditView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shijianan on 2017/1/4.
 */

public class ServerEditPresenterImpl implements ServerEditPresenter,ServerEditModel.OnQueryListener,ServerEditModel.OnCheckUIListener,ServerEditModel.OnInsertListener,ServerEditModel.OnUpdateListener {

    private ServerEditModel serverEditModel;
    private ServerEditView serverEditView;

    public ServerEditPresenterImpl(ServerEditView serverEditView){
        this.serverEditView = serverEditView;
        this.serverEditModel = new ServerEditModelImpl();
    }

    @Override
    public void onQuerySuccess(Password password, ServerPassword serverPassword, List<PassLabel> passLabelList) {
        serverEditView.onQuerySuccess(password,serverPassword,passLabelList);
    }

    @Override
    public void onQueryError(String msg) {
        serverEditView.onQueryError(msg);
    }

    @Override
    public void queryLabel(String uuid) {
        serverEditModel.query(uuid,this);
    }

    @Override
    public void checkUI(ServerPassword primaryPassword, String title, String address, String port, String account, String password, String remark, ArrayList<PassLabel> primaryLabels, ArrayList<PassLabel> extraLabels) {
        serverEditModel.checkUI(primaryPassword,title,address,port,account,password,remark,primaryLabels,extraLabels,this);
    }

    @Override
    public void insert(String title, String ip, String port, String account, String password, String remark, ArrayList<PassLabel> passLabelArrayList) {
        serverEditModel.insert(title,ip,port,account,password,remark,passLabelArrayList,this);
    }

    @Override
    public void update(ServerPassword serverPassword, String title, String ip, String port, String account, String password, String remark, ArrayList<PassLabel> passLabelArrayList) {
        serverEditModel.update(serverPassword,title,ip,port,account,password,remark,passLabelArrayList,this);
    }

    @Override
    public void onUIChange() {
        serverEditView.onUIChange();
    }

    @Override
    public void onUINotChange() {
        serverEditView.onUINotChange();
    }

    @Override
    public void onInputInvalid() {
        serverEditView.onInputInvalid();
    }

    @Override
    public void onInsertSuccess(ServerPassword serverPassword) {
        serverEditView.onInsertSuccess(serverPassword);
    }

    @Override
    public void onInsertEmptyTitle() {
        serverEditView.onInsertEmptyTitle();
    }

    @Override
    public void onInsertIllegalIP() {
        serverEditView.onInsertIllegalIP();
    }

    @Override
    public void onInsertIllegalPort() {
        serverEditView.onInsertIllegalPort();
    }

    @Override
    public void onInsertEmptyAccount() {
        serverEditView.onInsertEmptyAccount();
    }

    @Override
    public void onInsertEmptyPassword() {
        serverEditView.onInsertEmptyPassword();
    }

    @Override
    public void onInsertError(String msg) {
        serverEditView.onInsertError(msg);
    }

    @Override
    public void onUpdateSuccess(String uuid) {
        serverEditView.onUpdateSuccess(uuid);
    }

    @Override
    public void onUpdateEmptyTitle() {
        serverEditView.onUpdateEmptyTitle();
    }

    @Override
    public void onUpdateIllegalIP() {
        serverEditView.onUpdateIllegalIP();
    }

    @Override
    public void onUpdateIllegalPort() {
        serverEditView.onUpdateIllegalPort();
    }

    @Override
    public void onUpdateEmptyAccount() {
        serverEditView.onUpdateEmptyAccount();
    }

    @Override
    public void onUpdateEmptyPassword() {
        serverEditView.onUpdateEmptyPassword();
    }

    @Override
    public void onUpdateError(String msg) {
        serverEditView.onUpdateError(msg);
    }
}
